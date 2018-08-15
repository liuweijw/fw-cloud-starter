package com.github.liuweijw.provider.file.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.github.liuweijw.commons.base.R;
import com.github.liuweijw.provider.core.beans.FileModel;

/**
 * file manage center feign api
 * 
 * @author liuweijw
 */
@Api(value = "API - FileUploadApi - 文件上传下载", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface FileUploadApi {

	/**
	 * 描述 : 上传文件
	 *
	 * @param pathCode
	 *            路径代码
	 * @param file
	 *            文件
	 * @return 结果
	 * @throws IOException
	 *             异常
	 */
	@ApiOperation(value = "文件上传", notes = "上传文件")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", name = "pathCode", value = "路径代码", required = true, dataType = "string"),
			@ApiImplicitParam(paramType = "form", name = "pathType", value = "模块代码", required = true, dataType = "string"),
			@ApiImplicitParam(paramType = "form", name = "file", value = "文件", required = true, dataType = "__file") })
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	R<FileModel> upload(String pathCode, String pathType, MultipartFile file) throws IOException;

	/**
	 * 描述 : 批量上传文件
	 *
	 * @param pathCode
	 *            路径代码
	 * @param files
	 *            文件清单
	 * @return 结果
	 * @throws IOException
	 *             异常
	 */
	@ApiOperation(value = "批量上传", notes = "批量上传文件")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", name = "pathCode", value = "路径代码", required = true, dataType = "string"),
			@ApiImplicitParam(paramType = "form", name = "files", value = "文件清单", required = true, allowMultiple = true, dataType = "__file") })
	@RequestMapping(value = "/file/upload/batch", method = RequestMethod.POST)
	R<List<FileModel>> batchUpload(String pathCode, String pathType, List<MultipartFile> files) throws IOException;

	/**
	 * 描述 : 通过文件ID访问文件
	 *
	 * @param fileParam
	 *            文件参数
	 * @param response
	 *            响应对象
	 * @throws IOException
	 *             异常
	 */
	@ApiOperation(value = "文件下载", notes = "通过文件ID访问文件")
	@ApiImplicitParam(name = "attId", value = "文件attId", required = true, dataType = "string", paramType = "path")
	@RequestMapping(value = "/file/download/{attId}", method = RequestMethod.POST)
	void download(@PathVariable("attId") String attId, HttpServletResponse response) throws IOException;

	/**
	 * 描述 : 通过文件ID删除文件
	 *
	 * @param fileParam
	 *            文件参数
	 * @return 文件信息
	 * @throws IOException
	 *             异常
	 */
	@ApiOperation(value = "文件信息", notes = "通过文件ID删除文件信息")
	@ApiImplicitParam(name = "attId", value = "文件attId", required = true, dataType = "string", paramType = "path")
	@RequestMapping(value = "/file/del/{attId}", method = RequestMethod.POST)
	R<Boolean> del(@PathVariable("attId") String attId) throws IOException;

}
