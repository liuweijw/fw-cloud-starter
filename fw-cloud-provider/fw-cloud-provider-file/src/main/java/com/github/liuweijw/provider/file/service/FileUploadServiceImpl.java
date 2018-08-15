package com.github.liuweijw.provider.file.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.activation.FileTypeMap;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.commons.utils.RandomHelper;
import com.github.liuweijw.commons.utils.StringHelper;
import com.github.liuweijw.provider.config.properties.ConfigProperties;
import com.github.liuweijw.provider.config.properties.FileProperties;
import com.github.liuweijw.provider.core.beans.FileModel;
import com.github.liuweijw.provider.core.beans.FileMsgModel;
import com.github.liuweijw.provider.core.constant.FileConstant;
import com.github.liuweijw.provider.core.exception.FileException;
import com.github.liuweijw.provider.core.utils.FileUtil;
import com.github.liuweijw.provider.rabbitmq.beans.MqMessage;
import com.github.liuweijw.provider.rabbitmq.service.MqService;
import com.github.liuweijw.provider.rabbitmq.utils.MqUtil;
import com.xiaoleilu.hutool.date.DateUtil;

/**
 * 文件上传、下载、删除接口
 * 
 * @author liuweijw
 */
@Slf4j
@Component
public class FileUploadServiceImpl implements FileUploadService {

	@Autowired
	private ConfigProperties	configProperties;

	/**
	 * 此处一般处理异步入库之类需求，如果不需要，可以去掉
	 */
	@Autowired
	private MqService			mqService;

	@Override
	public FileModel upload(String pathCode, String pathType, MultipartFile file) throws IOException {
		FileProperties fileProperties = configProperties.getFile();
		// 验证配置是否正确
		this.verification(pathCode, pathType, file);
		// 创建返回对象
		FileModel fileModel = new FileModel();
		// 文件上传路径code
		fileModel.setPathCode(pathCode);
		// 文件上传模块名称
		fileModel.setPathType(pathType);
		// 保存编码
		fileModel.setAttCode(RandomHelper.randomStringUpper());
		// 获得rootPath // E:\\upload\xxxx\yyyyMMdd\nextId.jpg
		String rootPath = fileProperties.getRootPath(); // E:\\upload
		// 根据pathCode获得path
		String path = fileProperties.getPath().get(pathCode).getPath(); // \xxxx\
		// 获得当前时间戳
		String day = DateUtil.format(new Date(), "yyyyMMdd") + File.separator;
		// 获得文件名
		fileModel.setOriginalName(file.getOriginalFilename()); // nextId.jpg
		// 保存扩展名
		fileModel.setSuffix(FileUtil.getFilePrefix(fileModel.getOriginalName())); // jpg
		// 文件新名称
		fileModel.setReName(fileModel.getAttCode() + "." + fileModel.getSuffix()); // newName.jpg
		// 获得上下文类型
		fileModel.setContentType(file.getContentType());
		// 获得文件长度
		fileModel.setSize(file.getSize());
		// 拼接相对路径(目录)
		String relativePath = path + day; // \xxxx\yyyyMMdd\
		// 拼接相对路径(文件)
		fileModel.setRelativePath(FileUtil.formatFilePath(relativePath) + fileModel.getReName());
		// 文件Id 通过相对地址计算获取
		fileModel.setAttId(FileUtil.encodeBase64(fileModel.getRelativePath()));
		// 拼接绝对路径(目录)
		String absolutePath = rootPath + relativePath;
		// 获得文件对象(目录)
		File dest = new File(absolutePath);
		// 创建目录
		if (!dest.exists())
			dest.mkdirs();
		// 拼接绝对路径(文件)
		String absoluteFilePath = rootPath + fileModel.getRelativePath();
		// 获得文件对象
		dest = new File(absoluteFilePath);
		// 保存
		file.transferTo(dest);

		// 异步文件信息入库
		log.info("[FileUploadServiceImpl 文件上传成功返回信息：{}]", fileModel.toString());
		MqMessage<FileModel> message = MqUtil.of(fileModel);
		mqService.send(message, FileConstant.MQ_FILE_UPLOAD_EXCHANGE, FileConstant.MQ_FILE_UPLOAD_ROUTINGKEY);

		return fileModel;
	}

	@Override
	public FileModel getFileModel(String attId) throws IOException {
		// 判断attId是否为空
		if (StringHelper.isBlank(attId))
			throw new FileException("文件attId can not be empty");

		FileProperties fileProperties = configProperties.getFile();
		// 返回对象
		FileModel result = new FileModel();
		result.setAttId(attId);
		// 获得相对路径
		String relativeFilePath = FileUtil.decodeBase64(result.getAttId());
		result.setRelativePath(relativeFilePath);
		// 拼接绝对路径(目录)
		String absolutePath = fileProperties.getRootPath() + result.getRelativePath();
		// 获得资源对象
		FileSystemResource fileResource = new FileSystemResource(absolutePath);
		// 判断资源是否存在
		if (!fileResource.exists())
			throw new FileException("file : " + relativeFilePath + " does not exist");

		// 获得contentType
		String contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(fileResource.getFile());
		result.setContentType(contentType);
		// 获得文件名称
		result.setReName(fileResource.getFilename());
		// 获得文件长度
		result.setSize(fileResource.contentLength());

		return result;
	}

	@Override
	public void delFile(String attId) throws IOException {
		if (StringHelper.isBlank(attId))
			throw new FileException("文件attId can not be empty");

		FileProperties fileProperties = configProperties.getFile();
		String relativeFilePath = FileUtil.decodeBase64(attId);
		// 拼接绝对路径(目录)
		String absolutePath = fileProperties.getRootPath() + relativeFilePath;
		// 获得资源对象
		FileSystemResource fileResource = new FileSystemResource(absolutePath);
		// 资源存在则删除
		if (fileResource.exists())
			fileResource.getFile().delete();

		// 异步文件信息入库
		FileMsgModel fileMsgModel = new FileMsgModel();
		fileMsgModel.setDel(true);
		fileMsgModel.setStatu(1);
		fileMsgModel.setAttIdArray(new String[] { attId });
		log.info("[FileUploadServiceImpl 文件删除成功异步消息通知业务系统：{}]", fileMsgModel.toString());
		mqService.send(MqUtil.of(fileMsgModel), FileConstant.MQ_FILE_MSG_EXCHANGE, FileConstant.MQ_FILE_MSG_ROUTINGKEY);

	}

	@Override
	public void download(String attId, HttpServletResponse response) throws IOException {
		FileModel fileModel = this.getFileModel(attId);
		FileProperties fileProperties = configProperties.getFile();
		// 设置response
		response.setContentLengthLong(fileModel.getSize());
		response.setCharacterEncoding(FileConstant.ENCODING);
		response.setContentType(fileModel.getContentType());
		// 获得rootPath
		String rootPath = fileProperties.getRootPath();
		// 拼接绝对路径(目录)
		String absolutePath = rootPath + fileModel.getRelativePath();
		// 获得资源对象
		FileSystemResource fsr = new FileSystemResource(absolutePath);
		// 输出文件
		final int buffInt = 1024;
		byte[] buff = new byte[buffInt];
		try (OutputStream os = response.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(fsr.getInputStream())) { // NOSONAR
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		}
	}

	private void verification(String pathCode, String pathType, MultipartFile file) {
		FileProperties fileProperties = configProperties.getFile();
		// 校验保存文件根路径是否配置
		if (PublicHelper.isEmpty(fileProperties.getRootPath()))
			throw new FileException("rootPath not defined");

		// 校验pathCode是否存在
		if (!fileProperties.getPath().containsKey(pathCode))
			throw new FileException("pathCode : " + pathCode + " not defined");

		if (PublicHelper.isEmpty(pathType))
			throw new FileException("pathType : " + pathType + " not defined");

		// 校验上传文件是否为空
		if (file.isEmpty())
			throw new FileException("file is empty");
	}

}
