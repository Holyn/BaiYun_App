package com.baiyun.util;

import com.baiyun.http.HttpURL;

public class URLUtil {
	/**
	 * "url" : "./Uploads/201501261050345728892457880.jpg",
	 * 图片url前面加上http://183.237.48.209:8085/ 就能访问了， 
	 * 如: http://183.237.48.209:8085/Uploads/201501261050345728892457880.jpg  
	 */
	public static String getFullImaeUrl(String url) {
		return HttpURL.HOST+url.substring(1);
	}
}
