package com.baiyun.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.util.Base64;

public class Base64Util {

	/**
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理 imgFilePath -> 图片路径
	 */
	public static String getImageStr(String imgFilePath) {
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
//		BASE64Encoder encoder = new BASE64Encoder();
		Base64.encodeToString(data, Base64.DEFAULT);
//		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
		return Base64.encodeToString(data, Base64.DEFAULT);
	}

	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 */
	public static boolean generateImage(String imgStr, String imgFilePath) {
		if (imgStr == null) // 图像数据为空
			return false;
//		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
//			byte[] bytes = decoder.decodeBuffer(imgStr);
			byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
