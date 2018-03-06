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

	/**
	 * 微信appId
	 */
	@Value("${weixin.appId}")
	private String appId;
	@Value("${weixin.appSecret}")
	private String appSecret;

	public String getAppId() {
		log.error("#getAppId=" + appId);
		return appId;
	}

	public String getAppSecret() {
		log.error("#getAppSecret=" + appSecret);
		return appSecret;
	}

}
