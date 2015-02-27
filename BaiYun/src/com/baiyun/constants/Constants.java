package com.baiyun.constants;

/**
 * 手机客户端的静态常量类
 * @author Holyn
 * @create 2015-1-18
 * @modified
 */
public class Constants {
	//设备类型
	public static final String DEVICE_TYPE = "Android";
	//1幼儿园版，2家庭版
	public static final String U_TYPE = "2";
	//平台标识，1是幼儿伙伴，2是翼幼通
	public static final String PLATFORM = "1";
	
	//利用sharepreference保存用户信息的xml文件名
	public static final String USER_INFO = "userInfo";
	//利用sharepreference保存App的一些设置信息的xml文件名
	public static final String APP_SETTING = "appSetting";
	
	//用户加密数据的秘钥
	public static final String SEED_AES = "holyn";
	
	//apk下载的Notification的id
	public static final int ID_APK_DOWN_NOTI = 001;
	
	//intent的标识与value
	public static final String INTENT_EXIT_STATE = "intent_exit_state";
	public static final String SYSTEM_EXIT = "system_exit";
	
	//通过Bundle或Itent传值的key
	public static final String KEY_BOOLEAN = "key_boolean";
	public static final String KEY_STRING = "key_string";
	public static final String KEY_ARTICLE_VO = "key_article_vo";
	public static final String KEY_ARTICLE_TYPE = "key_article_type";
	public static final String KEY_SWIPE_CARD_YTD_VO = "key_swipe_card_ytd_vo";
	public static final String KEY_ALBUM_TYPE = "key_album_type";
	public static final String KEY_ALBUM_VO = "key_album_vo";
	public static final String KEY_ALBUM_CLICK_POSITION = "key_album_click_position";
	public static final String KEY_PHOTO_VO = "key_photo_vo";
	public static final String KEY_PHOTO_URL = "key_photo_url";
	public static final String KEY_PHOTO_VO_LIST = "key_photo_vo_list";
	public static final String KEY_PHOTO_CLICK_POSITION = "key_photo_click_position";
	public static final String KEY_WEB_ART_VO = "key_web_art_vo";
	public static final String KEY_LOCAL_IMAGE_V0_LIST = "key_local_image_vo_list";
	public static final String KEY_MORE_ACTIVITY_TYPE = "key_more_activity_type";
	public static final String KEY_SMS_VO = "key_sms_vo";
	public static final String KEY_RESOURCE_VO = "key_resource_vo";
	public static final String KEY_TERM_VO = "key_term_vo";
	public static final String KEY_PERIOD_SCHOOL_VO = "key_period_school_vo";
	public static final String KEY_PERIOD_HOME_VO = "key_period_home_vo";
	public static final String KEY_MEALS_VO = "key_meals_vo";
	
}
