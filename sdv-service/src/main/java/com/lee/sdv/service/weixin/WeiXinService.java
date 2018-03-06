package com.lee.sdv.service.weixin;

public interface WeiXinService {

	/**
	 * 通过code获取用户session_key
	 * 
	 * @param jsCode
	 * @return
	 */
	public String jscode2session(String jsCode);

}
