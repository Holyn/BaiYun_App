package com.baiyun.http;

/**
 * 编码	状态
 * 
 * 0000	返回数据成功
 * 0001	返回数据失败
 * 0002	导入数据成功
 * 0003	导入数据失败
 * 0004	登录成功
 * 0005	用户名不存在或密码错误
 * 0006	验证码出错
 * 0007	已是最新版本
 * 0008	旧密码有误
 * 0009	用户名不存在
 *
 */
public class HttpRecode {
	public static final String GET_SUCCESS = "0000";
	public static final String GET_ERROR = "0001";
}
