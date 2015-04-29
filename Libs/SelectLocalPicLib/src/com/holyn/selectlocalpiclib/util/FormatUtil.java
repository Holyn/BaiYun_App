package com.holyn.selectlocalpiclib.util;

/**
 * byte[]、InputStream、Bitmap、Drawable 直接的转换工具类
 * @author Holyn
 * @create 2014-8-18
 * @modified
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class FormatUtil {
	private static FormatUtil tools = new FormatUtil();

	public static FormatUtil getInstance() {
		if (tools == null) {
			tools = new FormatUtil();
			return tools;
		}
		return tools;
	}

	// 将byte[]转换成InputStream
	public InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	// 将InputStream转换成byte[]
	public byte[] InputStream2Bytes(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将Bitmap转换成InputStream
	public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将InputStream转换成Bitmap
	public Bitmap InputStream2Bitmap(InputStream is) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int byteread = 0;

		try {
			while ((byteread = is.read(buffer)) != -1) {

				outStream.write(buffer, 0, byteread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();// 关闭输入流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
					;// 关闭输出流
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		byte[] data = outStream.toByteArray();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length, opts);

		return bm;
	}

	// Drawable转换成InputStream
	public InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = this.drawable2Bitmap(d);
		return this.Bitmap2InputStream(bitmap);
	}

	// InputStream转换成Drawable
	public Drawable InputStream2Drawable(InputStream is) {
		Bitmap bitmap = this.InputStream2Bitmap(is);
		return this.bitmap2Drawable(bitmap);
	}

	// Drawable转换成byte[]
	public byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap = this.drawable2Bitmap(d);
		return this.Bitmap2Bytes(bitmap);
	}

	// byte[]转换成Drawable
	public Drawable Bytes2Drawable(byte[] b) {
		Bitmap bitmap = this.Bytes2Bitmap(b);
		return this.bitmap2Drawable(bitmap);
	}

	// Bitmap转换成byte[]
	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// byte[]转换成Bitmap
	public Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	// Drawable转换成Bitmap
	public Bitmap drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable
				.getIntrinsicHeight(),
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	// Bitmap转换成Drawable
	public Drawable bitmap2Drawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}
}
