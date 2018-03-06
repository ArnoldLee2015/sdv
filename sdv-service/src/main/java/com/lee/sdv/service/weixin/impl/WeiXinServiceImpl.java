package com.lee.sdv.service.weixin.impl;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.lee.sdv.common.utils.WeixinUtil;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.manager.SdvUserManager;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.service.weixin.domain.ApiConfig;
import com.lee.sdv.service.weixin.domain.SessionBean;
@Service
public class WeiXinServiceImpl implements WeiXinService {

	private static final int EXPIRE_DAYS = 7;
	@Autowired
	private ApiConfig apiConfig;
	@Autowired
	private SdvUserManager sdvUserManager;

	@Override
	public String jscode2session(String jsCode) {
		if (Strings.isNullOrEmpty(jsCode)) {
			return "";
		}
		String url = String.format(ApiConfig.jscode2session, apiConfig.getAppId(), apiConfig.getAppSecret(), jsCode);
		String content = WeixinUtil.httpRequests(url, WeixinUtil.HTTP_GET, null);
		if (Strings.isNullOrEmpty(content)) {
			return "";
		}
		SessionBean session = JSON.parseObject(content, SessionBean.class);
		if (session != null) {
			String localSessionKey = UUID.randomUUID().toString().replace("-", "");
			SdvUser sdvUser = sdvUserManager.selectByOpenId(session.getOpenid());
			if (sdvUser == null) {
				sdvUser = new SdvUser();
				sdvUser.setOpenId(session.getOpenid());
				sdvUser.setSessionKey(session.getSession_key());
				sdvUser.setUnionId(session.getUnionid());
				sdvUser.setLocalSessionKey(localSessionKey);
				sdvUser.setIsDelete(0);
				Calendar c = Calendar.getInstance();
				sdvUser.setCreateTime(c.getTime());
				c.add(Calendar.DAY_OF_YEAR, EXPIRE_DAYS);
				sdvUser.setExpireDate(c.getTime());
				sdvUser.setWechatAccount(jsCode);
				sdvUserManager.insertEntry(sdvUser);
			} else {
				sdvUser.setOpenId(session.getOpenid());
				sdvUser.setSessionKey(session.getSession_key());
				sdvUser.setUnionId(session.getUnionid());
				sdvUser.setLocalSessionKey(localSessionKey);
				Calendar c = Calendar.getInstance();
				sdvUser.setUpdateTime(c.getTime());
				c.add(Calendar.DAY_OF_YEAR, EXPIRE_DAYS);
				sdvUser.setExpireDate(c.getTime());
				sdvUser.setWechatAccount(jsCode);
				sdvUserManager.updateByKey(sdvUser);
			}
			return localSessionKey;
		}
		return "";
	}

}
