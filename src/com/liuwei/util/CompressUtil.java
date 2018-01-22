package com.liuwei.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liuwei.main.Entrance;

/**
 * @Project: news-crawling
 * @Class: CompressUtil.java
 * @Description: 压缩工具类
 * @Date: 2017年11月19日
 * @author liuwei5
 */
public class CompressUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(CompressUtil.class);

	/**
	 * 解压
	 * @param inputByte 待解压缩的字节数组
	 * @return 解压缩后的字节数组
	 */
	public static byte[] uncompress(byte[] inputByte){
		int len = 0;
		Inflater infl = new Inflater();
		infl.setInput(inputByte);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] outByte = new byte[1024];
		try {
			while (!infl.finished()) {
				// 解压缩并将解压缩后的内容输出到字节输出流bos中
				len = infl.inflate(outByte);
				if (len == 0) {
					break;
				}
				bos.write(outByte, 0, len);
			}
			infl.end();
		} catch (Exception e) {
			logger.error(e.toString());
			logger.info("====== 解压出现错误!!! =======");
		} finally {
			try {
				if (bos!=null) bos.close();
			} catch (IOException e) {
				logger.error(e.toString());
				logger.info("====== 流关闭出现错误!!! =======");
			}
		}
		return bos.toByteArray();
	}

	/**
	 * 压缩
	 * @param inputByte 待压缩的字节数组
	 * @return 压缩后的数据
	 */
	public static byte[] compress(byte[] inputByte) {
		int len = 0;
		Deflater defl = new Deflater();
		defl.setInput(inputByte);
		defl.finish();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] outputByte = new byte[1024];
		try {
			while (!defl.finished()) {
				// 压缩并将压缩后的内容输出到字节输出流bos中
				len = defl.deflate(outputByte);
				bos.write(outputByte, 0, len);
			}
			defl.end();
		} catch(Exception exception){
			logger.error(exception.toString());
			logger.info("====== 压缩时出现错误!!! =======");
		}finally {
			try {
				if(bos!=null) bos.close();
			} catch (IOException e) {
				logger.error(e.toString());
				logger.info("====== 流关闭出现错误!!! =======");
			}
		}
		return bos.toByteArray();
	}
}
