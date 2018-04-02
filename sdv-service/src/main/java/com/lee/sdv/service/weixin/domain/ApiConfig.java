package com.lee.sdv.service.weixin.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("apiConfig")
public class ApiConfig {
	private final static Log log = LogFactory.getLog(ApiConfig.class);

	private static final String domain = "https://api.weixin.qq.com";

	public static final String jscode2session = domain + "/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	public static final String accessToken = domain +"/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	public static final String refreshToken=  domain +"/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
	public static final String accessToken2UserInfo = domain +"/sns/userinfo?access_token=%s&openid=%s";
	public static final String loginCode="https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
	/**
	 * 微信appId
	 */
	@Value("${weixin.appId}")
	private String appId;
	@Value("${weixin.appSecret}")
	private String appSecret;
	@Value("${weixin.open.appId}")
	private String appOpenId;
	@Value("${weixin.open.appSecret}")
	private String appOpenSecret;

	public String getAppId() {
		log.error("#getAppId=" + appId);
		return appId;
	}

	public String getAppSecret() {
		log.error("#getAppSecret=" + appSecret);
		return appSecret;
	}

	public String getAppOpenId() {
		return appOpenId;
	}

	public String getAppOpenSecret() {
		return appOpenSecret;
	}

}
