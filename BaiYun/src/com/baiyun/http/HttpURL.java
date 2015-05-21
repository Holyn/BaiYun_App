package com.baiyun.http;

public class HttpURL {
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	/* 服务器地址 */
	public final static String HOST = HTTP+"app.byxy.com:8080";
	public final static String URL_PRE = HOST+"/app/";
	
	/* 参数前缀 */
	public final static String LIMIT_PARAM = "&limit=";
	public final static String PAGE_PARAM = "&page=";
	public final static String ID_PARAM = "&id=";
	
	public final static String PARAM_ID = "id";
	public final static String PARAM_IMG = "img";
	
	/**
	 * 首页-广告图片
	 * http://app.byxy.com:8080/app/homePage?type=10
	 */
	public final static String HOME_AD = URL_PRE + "homePage?type=10";
	
	/**
	 * 首页-新闻
	 * http://app.byxy.com:8080/app/homePage?type=11
	 */
	public final static String HOME_NEWS = URL_PRE + "homePage?type=11";
	
	/**
	 * 首页-白云在线网站
	 * http://app.byxy.com:8080/app/homePage?type=12
	 */
	public final static String HOME_ONLINE = URL_PRE + "homePage?type=12";
	
	/**
	 * 首页-学校概况(顶部图片)
	 * http://app.byxy.com:8080/app/schoolIntroPic
	 */
	public final static String SCHOOL_INTRO_PIC = URL_PRE + "schoolIntroPic";
	
	/**
	 * 首页-学校概况-学校简介
	 * http://app.byxy.com:8080/app/schoolIntroContent?type=100
	 */
	public final static String SCHOOL_INTRO = URL_PRE + "schoolIntroContent?type=100";
	
	/**
	 * 首页-学校概况-组织架构
	 * http://app.byxy.com:8080/app/schoolIntroContent?type=101
	 */
	public final static String SCHOOL_ORGANIZATION = URL_PRE + "schoolIntroContent?type=101";
	
	/**
	 * 首页-学校概况-师资队伍-介绍
	 * http://app.byxy.com:8080/app/schoolIntroContent?type=102
	 */
	public final static String SCHOOL_TEACHERS_SUM = URL_PRE + "schoolIntroContent?type=102";
	
	/**
	 * 首页-学校概况-师资队伍-列表
	 * http://app.byxy.com:8080/app/schoolIntroContent?type=1020&limit=10&page=1
	 */
	public final static String SCHOOL_TEACHERS_LIST = URL_PRE + "schoolIntroContent?type=1020&limit=10&page=";
	
	/**
	 * 首页-学校概况-师资队伍-列表(更多)
	 * http://app.byxy.com:8080/app/teacherIntro?limit=12&page=1&id=
	 */
	public final static String SCHOOL_TEACHERS_LIST_MORE = URL_PRE + "teacherIntro?limit=10";
	
	/**
	 * 首页-学校概况-学校环境
	 * http://app.byxy.com:8080/app/schoolIntroContent?type=103
	 */
	public final static String SCHOOL_ENVIRONMENT = URL_PRE + "schoolIntroContent?type=103";
	
	/**
	 * 首页-技师微报
	 * http://app.byxy.com:8080/app/schoolNews?page=1&limit=10
	 */
	public final static String SCHOOL_NEWS = URL_PRE + "schoolNews?limit=10&page=";
	
	/**
	 * 首页-技师微报-详情图文链接
	 * http://app.byxy.com:8080/app/schoolNewsDetails?id=
	 */
	public final static String SCHOOL_NEWS_DETAILS = URL_PRE + "schoolNewsDetails?id=";
	
	/**
	 * 首页-视频白云（类型列表，每个类型最多有3条视频）
	 * http://app.byxy.com:8080/app/schoolVideo?limit=10&page=1
	 */
	public final static String SCHOOL_VIDEO_STYLE = URL_PRE + "schoolVideo?limit=10&page=";
	
	/**
	 * 首页-视频白云（视频列表）
	 * http://app.byxy.com:8080/app/schoolVideoDetails?id=（3.5接口返回的视频类型id）&page=页数&limit=一页里的最多条数
	 */
	public final static String SCHOOL_VIDEO = URL_PRE + "schoolVideoDetails?limit=12";
	
	/**
	 * 首页-系部设置
	 * http://app.byxy.com:8080/app/schoolDepartment
	 */
	public final static String SCHOOL_DEPARTMENT = URL_PRE + "schoolDepartment";
	
	/**
	 * 首页-系部设置-系部的图文信息接口
	 * http://app.byxy.com:8080/app/schoolDepartmentDetails?id=
	 */
	public final static String SCHOOL_DEPARTMENT_DETAIL = URL_PRE + "schoolDepartmentDetails?id=";
	
	/**
	 * 首页-就业服务-就业概况
	 * http://app.byxy.com:8080/app/schoolService?type=104
	 */
	public final static String HOME_JOB_SUMMARY = URL_PRE + "schoolService?type=104";
	
	/**
	 * 首页-就业服务-招聘信息
	 * http://app.byxy.com:8080/app/schoolService?type=105&limit=10&page=1
	 */
	public final static String HOME_JOB_RECRUIT = URL_PRE + "schoolService?type=105&limit=10&page=";
	
	/**
	 * 首页-就业服务-校企合作-概况
	 * http://app.byxy.com:8080/app/schoolService?type=106
	 */
	public final static String HOME_JOB_COO_SUMMARY = URL_PRE + "schoolService?type=106";
	
	/**
	 * 首页-就业服务-校企合作-概况
	 * http://app.byxy.com:8080/app/schoolService?type=1060&limit=10&page=1
	 */
	public final static String HOME_JOB_COO_NEWS = URL_PRE + "schoolService?type=1060&limit=10&page=";
	
	/**
	 * 首页-交通查询-交通指南
	 * http://app.byxy.com:8080/app/trafficQuery?type=107
	 */
	public final static String TRAFFIC_GUIDE = URL_PRE + "trafficQuery?type=107";
	
	/**
	 * 首页-交通查询-公交车查询
	 * http://app.byxy.com:8080/app/trafficQuery?type=108&limit=10&page=1
	 */
	public final static String TRAFFIC_SEARCH = URL_PRE + "trafficQuery?type=108&limit=10&page=";
	
	/**
	 * 招生服务-减免学费申请指南
	 * http://app.byxy.com:8080/app/recuritInfor?type=20
	 */
	public final static String RECRUIT_INFOR_20 = URL_PRE + "recuritInfor?type=20";
	
	/**
	 * 招生服务-新生入学须知
	 * http://app.byxy.com:8080/app/recuritInfor?type=19
	 */
	public final static String RECRUIT_INFOR_19 = URL_PRE + "recuritInfor?type=19";
	
	/**
	 * 招生服务-招生类型
	 * http://app.byxy.com:8080/app/recuritInfor?type=16
	 */
	public final static String RECRUIT_INFOR_16 = URL_PRE + "recuritInfor?type=16";
	
	/**
	 * 招生服务-网上报名
	 * http://app.byxy.com:8080/app/recuritInfor?type=23
	 */
	public final static String RECRUIT_INFOR_23 = URL_PRE + "recuritInfor?type=23";
	
	/**
	 * 招生服务-网上报名-录取查询
	 * http://app.byxy.com:8080/app/recruitResultCheck?name=报名人姓名&telephone=报名人报名表上填的手机号
	 */
	public final static String TELEPHONE_PARAM = "&telephone=";
	public final static String RECRUIT_RESULT_CHECK = URL_PRE + "recruitResultCheck?name=";
	
	/**
	 * 招生服务-网上报名-专业选择
	 * http://app.byxy.com:8080/app/applyCourse?type=32
	 */
	public final static String APPLY_COURSE = URL_PRE + "applyCourse?type=";
	
	/**
	 * 招生服务-网上报名-导入报名表1接口：导入（年制高技+成人大专招生、四年制技师+成人本科招生、
	 * 五年制高技+成人大专招生三年制中级技工招生）报名表至后台 
	 * http://app.byxy.com:8080/app/onlineRegisterForm?id=
	 */
	public final static String APPLY_FORM_1 = URL_PRE + "onlineRegisterForm";
	
	/**
	 * 招生服务-网上报名-导入报名表2接口：导入（职业技能培训招生）报名表至后台 
	 * http://app.byxy.com:8080/app/trainingRegisterForm?id=
	 */
	public final static String APPLY_FORM_2 = URL_PRE + "trainingRegisterForm";
	
	/**
	 * 校园生活--（获取学工动态、体育创新、科技创新上方的图片）
	 * type=24,学工动态（id=24）、体育创新（id=26）、科技创新（id=27）
	 * http://app.byxy.com:8080/app/schoolLifeDetails?type=24&id=26&limit=10&page=1
	 */
	public final static String LIFE_NEWS_AD = URL_PRE + "schoolLifeDetails?type=24&id=";
	
	/**
	 * 校园生活--（获取学工动态、体育创新、科技创新下方的新闻）
	 * type=24,学工动态（id=24）、体育创新（id=26）、科技创新（id=27）
	 * http://app.byxy.com:8080/app/schoolLifeDetails?type=24&id=26&limit=10&page=1
	 */
	public final static String LIFE_NEWS_LIST = URL_PRE + "schoolLifeDetails?type=24&limit=20&id=";
	
	/**
	 * 校园生活--学生社团(包含社团解析的图文链接)
	 * http://app.byxy.com:8080/app/schoolLifeDetails?type=25
	 */
	public final static String LIFE_ASSOCIATION = URL_PRE + "schoolLifeDetails?type=25";
	
	/**
	 * 校园生活--学生社团--社团动态新闻
	 * 访问地址：http://app.byxy.com:8080/app/associationNews?
	 * id=（接口{@link LIFE_ASSOCIATION}返回的社团id)&page=页数&limit=一页里最多的条数
	 */
	public final static String LIFE_ASSOCIATION_NEWS = URL_PRE + "associationNews?limit=10&id=";
	
	/**
	 * 校园生活--榜样白云(List)
	 * http://app.byxy.com:8080/app/schoolLifeDetails?type=26&limit=10&page=1
	 */
	public final static String LIFE_MODEL = URL_PRE + "schoolLifeDetails?type=26&limit=12&page=";
	
	/**
	 * 校园生活--服务指南
	 * http://app.byxy.com:8080/app/schoolLifeDetails?type=27
	 */
	public final static String LIFE_GUIDE = URL_PRE + "schoolLifeDetails?type=27";
	
	/**
	 * 校内服务--通知公告
	 * http://app.byxy.com:8080/app/serviceType?type=31&limit=10&page=1
	 */
	public final static String S_NOTICE = URL_PRE + "serviceType?type=31&limit=10&page=";
	
	/**
	 * 校内服务--失物招领
	 * http://app.byxy.com:8080/app/getLostAndFound?userName=
	 */
	public final static String S_LOST_FOUND = URL_PRE + "getLostAndFound";
	
	/**
	 * 校内服务--办公电话
	 * http://app.byxy.com:8080/app/serviceType?type=38
	 */
	public final static String S_PHONE = URL_PRE + "serviceType?type=38";
	
	/**
	 * 校内服务--故障报修
	 * http://app.byxy.com:8080/app/serviceType?type=42
	 */
	public final static String S_REPAIRS = URL_PRE + "serviceType?type=42";
	
	/**
	 * 右侧菜单--获取验证码
	 * http://app.byxy.com:8080/app/getRandomStringImage
	 */
	public final static String S_GET_RANDOM_STRING_IMAGE = URL_PRE + "getRandomStringImage";
	
	/**
	 * 右侧菜单--导入百度推送所需的mobileChannelId和mobileUserId接口
	 * http://app.byxy.com:8080/app/setPush?mobileChannelId=3656702722203844290&mobileUserId=1460406946
	 */
	public final static String PARAM_MOBILE_CHANNEL_ID = "mobileChannelId";
	public final static String PARAM_MOBILE_USER_ID = "mobileUserId";
	public final static String R_SET_PUSH = URL_PRE + "setPush";//接口
	
	/**
	 * 右侧菜单--登录
	 * url=URL+/app/userLogin?userName=用户输入的用户名
	 * &password=用户输入的密码
	 * &randomString=（用户输入的验证码）
	 * &mobileChannelId=手机mobileChannelId
	 * &mobileUserId=手机mobileUserId
	 * 客户端输入用户登录里的用户名、密码、验证码和推送所需的mobileChannelId和mobileUserId后，
	 * 点击【登录】，发送url请求，获取用户是否成功登录信息和导入推送关联的用户
	 * 
	 * http://app.byxy.com:8080/app/userLogin?
	 * userName=admin&password=123456&randomString=2837&mobileChannelId=3656702722203844290&mobileUserId=1460406946
	 */
	public final static String PARAM_PASSWORD = "password";
	public final static String PARAM_RANDOM_STRING = "randomString";
	public final static String PARAM_USER_NAME = "userName";
	public final static String R_LOGIN = URL_PRE + "userLogin";//接口
	
	/**
	 * 右侧菜单--上传个人头像接口（与用户id关联）
	 * http://app.byxy.com:8080/app/upLoadUserImg?id=用户id&img=（通过base64编码后的图片编码）
	 */
	public final static String R_UPLOAD_USER_IMG = URL_PRE + "upLoadUserImg";
	
	/**
	 * 右侧菜单--使用工具--班车表
	 * http://app.byxy.com:8080/app/utilityToolDetails?id=168
	 */
	public final static String R_TOOLS_BUS = URL_PRE + "utilityToolDetails?id=168";
	
	/**
	 * 右侧菜单--使用工具--地铁路线图
	 * http://app.byxy.com:8080/app/utilityToolDetails?id=169
	 */
	public final static String R_TOOLS_METRO = URL_PRE + "utilityToolDetails?id=169";
	/**
	 * 右侧菜单--使用帮助
	 * http://app.byxy.com:8080/app/settingDetails?id=129
	 */
	public final static String R_HELP = URL_PRE + "settingDetails?id=129";
	
	/**
	 * 右侧菜单--关于我们
	 * http://app.byxy.com:8080/app/settingDetails?id=171
	 */
	public final static String R_ABOUT = URL_PRE + "settingDetails?id=171";
	
	/**
	 * 右侧菜单--版本更新
	 * http://app.byxy.com:8080/app/settingDetails?id=128&version=1
	 */
	public final static String R_VERSION = URL_PRE + "settingDetails?id=128&version=";
	
	/**
	 * 右侧菜单--意见反馈
	 * http://app.byxy.com:8080/app/advice?description=好好学习天天向上&creater=Holyn
	 */
	public final static String PARAM_DESCRIPTION = "description";
	public final static String PARAM_CREATER = "creater";
	public final static String R_ADVICE = URL_PRE + "advice";
	
	/**
	 * 右侧菜单--修改密码
	 */
}
