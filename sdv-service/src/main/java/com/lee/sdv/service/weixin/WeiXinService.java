package com.lee.sdv.service.weixin;

import com.lee.sdv.domain.SdvUser;

public interface WeiXinService {

	/**
	 * 通过jscode获取用户session_key
	 * 
	 * @param jsCode
	 * @param encryptedData
	 * @param iv
	 * @return
	 */
	public SdvUser jscode2session(String jsCode, String encryptedData, String iv);

	/**
	 * 通过code获取授权令牌
	 * 
	 * @param code
	 * @return 用户信息
	 */
	public SdvUser accessToken(String code);

	/**
	 * 通过refresh_token刷新授权令牌
	 * 
	 * @param sdvUser
	 * @return 用户信息
	 */
	public SdvUser refreshToken(SdvUser sdvUser);

	/**
	 * 通过access_token和openid获取用户的基础信息，包括头像、昵称、性别、地区
	 * 
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public String accessToken2UserInfo(String accessToken, String openId);
}
