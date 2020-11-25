package com.sailmi.core.oss.common.util;

import com.sailmi.core.tool.utils.IoUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件下载工具类
 *
 */
public class DownloadFileUtils {


	/**
	 * 把byte数组数据转换成文件
	 * @param response
	 * @param bytes    上传文件转换的字节数组
	 * @param fileName 上传文件的文件名  格式 文件名.文件类型 ,如 abc.txt
	 * @throws IOException
	 */
	public static void getFileByBytes(HttpServletResponse response, byte[] bytes, String fileName) throws IOException {
		//此处需要设置ISO8859-1，application/octet-stream为未知文件类型时使用
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		BufferedOutputStream output = null;
		//将文件以文件流的方式输出
		output = new BufferedOutputStream(response.getOutputStream());
		String fileNameDown = new String(fileName.getBytes(), "ISO8859-1");
		//fileNameDown上面得到的文件名
		response.setHeader("Content-Disposition", "attachment;filename=" +
			fileNameDown);
		output.write(bytes);
		response.flushBuffer();
		output.flush();
		output.close();
	}

	/**
	 * 把byte数组数据转换成文件
	 * @param bytes    上传文件转换的字节数组
	 * @param fileName 上传文件的文件名  格式 文件名.文件类型 ,如 abc.txt
	 * @throws IOException
	 */
	public static void getFileByBytes(byte[] bytes, String fileName) throws IOException {
		HttpServletResponse response = WebUtils.getResponse();
		//此处需要设置ISO8859-1，application/octet-stream为未知文件类型时使用
		response.setContentType("application/octet-stream;charset=ISO8859-1");
		BufferedOutputStream output = null;
		//将文件以文件流的方式输出
		output = new BufferedOutputStream(response.getOutputStream());
		String fileNameDown = new String(fileName.getBytes(), "ISO8859-1");
		//fileNameDown上面得到的文件名
		response.setHeader("Content-Disposition", "attachment;filename=" +
			fileNameDown);
		output.write(bytes);
		output.flush();
		output.close();
		response.flushBuffer();
	}

	/**
	 * 把文件流转换成文件
	 * @param in  文件流
	 * @param fileName  上传文件的文件名  格式 文件名.文件类型 ,如 abc.txt
	 * @throws IOException
	 */
	public static void getFileByInputStream(InputStream in, String fileName) throws IOException {
		getFileByBytes(IoUtil.toByteArray(in), fileName);
	}

	/**
	 * 文件下载
	 * @param bytes    上传文件转换的字节数组
	 * @param fileName 上传文件的文件名  格式 文件名.文件类型 ,如 abc.txt
	 * @return
	 */
	public static ResponseEntity<byte[]> getResponseEntityByByte(byte[] bytes, String fileName){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentDispositionFormData("attachment", fileName);
		//设置MIME类型
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.OK);
	}

	/**
	 * 文件下载
	 * @param in   文件流
	 * @param fileName  上传文件的文件名  格式 文件名.文件类型 ,如 abc.txt
	 * @return
	 * @throws IOException
	 */
	public static ResponseEntity<byte[]> getResponseEntityByByte(InputStream in, String fileName) throws IOException {
		return getResponseEntityByByte(IoUtil.toByteArray(in), fileName);
	}


	/**
	 *
	 * @param file
	 * @param fileName
	 * @throws Exception
	 */
	public static void compressFileWithZip(File file,String fileName) throws Exception {
//        HttpServletResponse response = WebUtils.getResponse();
		//定义压缩文件的名称
		File zipFile = new File(fileName);
		//定义输入文件流
		InputStream input = new FileInputStream(file);
		//定义压缩输出流
		ZipOutputStream zipOut = null;
		//实例化压缩输出流,并制定压缩文件的输出路径 就是D盘下,名字叫 demo.zip
		zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
		zipOut.putNextEntry(new ZipEntry(file.getName()));
		//设置注释
//        zipOut.setComment("www.demo.com");
		int temp = 0;
		while((temp = input.read()) != -1) {
			zipOut.write(temp);
		}
		input.close();
		zipOut.close();
	}
}
