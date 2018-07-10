package com.github.liuweijw.provider.core.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.github.liuweijw.commons.utils.PublicHelper;
import com.github.liuweijw.provider.core.constant.FileConstant;

/**
 * 文件工具类
 * 
 * @author liuweijw
 */
public class FileUtil {

	/**
	 * 默认UTF-8编码加密
	 * 
	 * @param filePath
	 *            加密字符串
	 * @return
	 *         null 加密失败
	 */
	public static String encodeBase64(String filePath) {
		if (PublicHelper.isEmpty(filePath)) return "";
		try {
			return new String(Base64.encodeBase64(filePath.getBytes(FileConstant.ENCODING)), FileConstant.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 默认UTF-8编码解密
	 * 
	 * @param filePath
	 *            解密字符串
	 * @return
	 *         null 解密失败
	 */
	public static String decodeBase64(String filePath) {
		if (PublicHelper.isEmpty(filePath)) return "";
		try {
			return new String(Base64.decodeBase64(filePath.getBytes(FileConstant.ENCODING)), FileConstant.ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getFilePrefix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	public static String formatFilePath(String path) {
		return path.replaceAll("\\\\", "/");
	}

	public static void main(String[] args) {
		// System.out.println(FileUtil.encodeBase64("/images/omc_1/20180709/B34EA8141BFA4D7993EA59B916F3EA4E.jpg"));
	}
}
