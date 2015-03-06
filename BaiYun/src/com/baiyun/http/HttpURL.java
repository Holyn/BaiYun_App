package com.baiyun.http;

public class HttpURL {
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	/* 服务器地址 */
	public final static String HOST = HTTP+"183.237.48.209:8085";
	public final static String URL_PRE = HOST+"/app/";
	
	/**
	 * 首页-广告图片
	 * http://183.237.48.209:8085/app/homePage?type=10
	 */
	public final static String HOME_AD = URL_PRE + "homePage?type=10";
	
	/**
	 * 首页-新闻
	 * http://183.237.48.209:8085/app/homePage?type=11
	 */
	public final static String HOME_NEWS = URL_PRE + "homePage?type=11";
	
	/**
	 * 首页-白云在线网站
	 * http://183.237.48.209:8085/app/homePage?type=12
	 */
	public final static String HOME_ONLINE = URL_PRE + "homePage?type=12";
	
	/**
	 * 首页-学校概况-学校简介
	 * http://183.237.48.209:8085/app/schoolIntroContent?type=100
	 */
	public final static String SCHOOL_INTRO = URL_PRE + "schoolIntroContent?type=100";
	
	/**
	 * 首页-学校概况-组织架构
	 * http://183.237.48.209:8085/app/schoolIntroContent?type=101
	 */
	public final static String SCHOOL_ORGANIZATION = URL_PRE + "schoolIntroContent?type=101";
	
	/**
	 * 首页-学校概况-师资队伍-介绍
	 * http://183.237.48.209:8085/app/schoolIntroContent?type=102
	 */
	public final static String SCHOOL_TEACHERS_SUM = URL_PRE + "schoolIntroContent?type=102";
	
	/**
	 * 首页-学校概况-师资队伍-列表
	 * http://183.237.48.209:8085/app/schoolIntroContent?type=1020
	 */
	public final static String SCHOOL_TEACHERS_LIST = URL_PRE + "schoolIntroContent?type=1020";
	
	/**
	 * 首页-学校概况-学校环境
	 * http://183.237.48.209:8085/app/schoolIntroContent?type=103
	 */
	public final static String SCHOOL_ENVIRONMENT = URL_PRE + "schoolIntroContent?type=103";
	
	/**
	 * 首页-技师微报
	 * http://183.237.48.209:8085/app/schoolNews?page=1&limit=10
	 */
	public final static String SCHOOL_NEWS = URL_PRE + "schoolNews?limit=10&page=";
	
	/**
	 * 首页-技师微报-详情图文链接
	 * http://183.237.48.209:8085/app/schoolNewsDetails?id=
	 */
	public final static String SCHOOL_NEWS_DETAILS = URL_PRE + "schoolNewsDetails?id=";
	
	/**
	 * 首页-视频白云
	 * http://183.237.48.209:8085/app/schoolVideo?limit=10&page=1
	 */
	public final static String SCHOOL_VIDEO = URL_PRE + "schoolVideo?limit=10&page=";
	
	/**
	 * 首页-系部设置
	 * http://183.237.48.209:8085/app/schoolDepartment
	 */
	public final static String SCHOOL_DEPARTMENT = URL_PRE + "schoolDepartment";
	
	/**
	 * 首页-系部设置-系部的图文信息接口
	 * http://183.237.48.209:8085/app/schoolDepartmentDetails?id=
	 */
	public final static String SCHOOL_DEPARTMENT_DETAIL = URL_PRE + "schoolDepartmentDetails?id=";
	
	/**
	 * 首页-就业服务-就业概况
	 * http://183.237.48.209:8085/app/schoolService?type=104
	 */
	public final static String HOME_JOB_SUMMARY = URL_PRE + "schoolService?type=104";
	
	/**
	 * 首页-就业服务-招聘信息
	 * http://183.237.48.209:8085/app/schoolService?type=105&limit=10&page=1
	 */
	public final static String HOME_JOB_RECRUIT = URL_PRE + "schoolService?type=105&limit=10&page=";
	
	/**
	 * 首页-就业服务-校企合作-概况
	 * http://183.237.48.209:8085/app/schoolService?type=106
	 */
	public final static String HOME_JOB_COO_SUMMARY = URL_PRE + "schoolService?type=106";
	
	/**
	 * 首页-就业服务-校企合作-概况
	 * http://183.237.48.209:8085/app/schoolService?type=1060&limit=10&page=1
	 */
	public final static String HOME_JOB_COO_NEWS = URL_PRE + "schoolService?type=1060&limit=10&page=";
	
	/**
	 * 首页-交通查询-交通指南
	 * http://183.237.48.209:8085/app/trafficQuery?type=107
	 */
	public final static String TRAFFIC_GUIDE = URL_PRE + "trafficQuery?type=107";
	
	/**
	 * 首页-交通查询-公交车查询
	 * http://183.237.48.209:8085/app/trafficQuery?type=108&limit=10&page=1
	 */
	public final static String TRAFFIC_SEARCH = URL_PRE + "trafficQuery?type=108&limit=10&page=";
	
	/**
	 * 招生服务-减免学费申请指南
	 * http://183.237.48.209:8085/app/recuritInfor?type=20
	 */
	public final static String RECRUIT_INFOR_20 = URL_PRE + "recuritInfor?type=20";
	
	/**
	 * 招生服务-新生入学须知
	 * http://183.237.48.209:8085/app/recuritInfor?type=19
	 */
	public final static String RECRUIT_INFOR_19 = URL_PRE + "recuritInfor?type=19";
	
	/**
	 * 招生服务-招生类型
	 * http://183.237.48.209:8085/app/recuritInfor?type=16
	 */
	public final static String RECRUIT_INFOR_16 = URL_PRE + "recuritInfor?type=16";
	
	/**
	 * 招生服务-网上报名
	 * http://183.237.48.209:8085/app/recuritInfor?type=23
	 */
	public final static String RECRUIT_INFOR_23 = URL_PRE + "recuritInfor?type=23";
	
	/**
	 * 招生服务-网上报名-专业选择
	 * http://183.237.48.209:8085/app/applyCourse?type=32
	 */
	public final static String APPLY_COURSE = URL_PRE + "applyCourse?type=";
	
	/**
	 * 招生服务-网上报名-导入报名表1接口：导入（年制高技+成人大专招生、四年制技师+成人本科招生、
	 * 五年制高技+成人大专招生三年制中级技工招生）报名表至后台 
	 * http://183.237.48.209:8085/app/onlineRegisterForm?id=
	 */
	public final static String APPLY_FORM_1 = URL_PRE + "onlineRegisterForm";
	
	/**
	 * 校园生活--（获取学工动态、体育创新、科技创新上方的图片和下方的新闻）
	 * type=24,学工动态（id=24）、体育创新（id=26）、科技创新（id=27）
	 * http://183.237.48.209:8085/app/schoolLifeDetails?type=24&id=26
	 */
	public final static String NEWS_24 = "24";
	
	public final static String LIFE_NEWS = URL_PRE + "schoolLifeDetails?type=24&id=";
}
