package com.lee.sdv.service.weixin.impl;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.lee.sdv.common.utils.AES;
import com.lee.sdv.common.utils.WeixinUtil;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.manager.SdvUserManager;
import com.lee.sdv.service.cos.impl.COSClientServiceImpl;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.service.weixin.domain.ApiConfig;
import com.lee.sdv.service.weixin.domain.SessionBean;

@Service
public class WeiXinServiceImpl implements WeiXinService {
	protected static final Logger LOG = LoggerFactory
			.getLogger(COSClientServiceImpl.class);
	private static final int EXPIRE_DAYS = 7;
	@Autowired
	private ApiConfig apiConfig;
	@Autowired
	private SdvUserManager sdvUserManager;

	@Override
	public SdvUser jscode2session(String jsCode, String encryptedData, String iv) {
		if (Strings.isNullOrEmpty(jsCode)) {
			return null;
		}
		String url = String.format(ApiConfig.jscode2session,
				apiConfig.getAppId(), apiConfig.getAppSecret(), jsCode);
		String content = WeixinUtil
				.httpRequests(url, WeixinUtil.HTTP_GET, null);
		LOG.error("weixin msg:[{}]", content);
		if (Strings.isNullOrEmpty(content)) {
			return null;
		}
		SessionBean session = JSON.parseObject(content, SessionBean.class);
		if (session != null && session.getOpenid() != null) {
			String localSessionKey = UUID.randomUUID().toString()
					.replace("-", "");
			SdvUser sdvUser = null;
			String nickName = "";
			try {
				String data = AES.decrypt(encryptedData,
						session.getSession_key(), iv, "UTF-8");
				LOG.error("encryptedData:[{}]", data);
				JSONObject json = JSON.parseObject(data);
				if (json.getString("unionId") != null) {
					nickName = json.getString("nickName") == null ? ""
							: URLEncoder.encode(json.getString("nickName"),
									"UTF-8");
					session.setUnionid(json.getString("unionId"));
					sdvUser = sdvUserManager.selectByUnionid(json
							.getString("unionId"));
				} else {
					sdvUser = sdvUserManager
							.selectByOpenId(session.getOpenid());
				}
			} catch (Exception e) {
				LOG.error("解密失败", e);
			}
			if (sdvUser == null) {
				sdvUser = new SdvUser();
				sdvUser.setName(nickName);
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
			return sdvUser;
		}
		return null;
	}

	@Override
	public SdvUser accessToken(String code) {
		String url = String.format(ApiConfig.accessToken,
				apiConfig.getAppOpenId(), apiConfig.getAppOpenSecret(), code);
		String content = WeixinUtil
				.httpRequests(url, WeixinUtil.HTTP_GET, null);
		LOG.error("weixin msg:[{}]", content);
		if (Strings.isNullOrEmpty(content)) {
			return null;
		}
		SessionBean session = JSON.parseObject(content, SessionBean.class);
		if (session != null && session.getAccess_token() != null) {
			SdvUser sdvUser = null;
			if (session.getUnionid() != null) {
				sdvUser = sdvUserManager.selectByUnionid(session.getUnionid());
			} else {
				sdvUser = sdvUserManager.selectByOpenId(session.getOpenid());
			}
			if (sdvUser == null) {
				sdvUser = new SdvUser();
				sdvUser.setOpenId(session.getOpenid());
				sdvUser.setSessionKey(session.getSession_key());
				sdvUser.setUnionId(session.getUnionid());
				sdvUser.setAccessToken(session.getAccess_token());
				sdvUser.setRefreshToken(session.getRefresh_token());
				sdvUser.setExpiresIn(session.getExpires_in());
				sdvUser.setIsDelete(0);
				Calendar c = Calendar.getInstance();
				sdvUser.setCreateTime(c.getTime());
				c.add(Calendar.DAY_OF_YEAR, EXPIRE_DAYS);
				sdvUser.setExpireDate(c.getTime());
				sdvUser.setWechatAccount(code);
				sdvUserManager.insertEntry(sdvUser);
			} else {
				sdvUser.setOpenId(session.getOpenid());
				sdvUser.setSessionKey(session.getSession_key());
				sdvUser.setUnionId(session.getUnionid());
				sdvUser.setAccessToken(session.getAccess_token());
				sdvUser.setRefreshToken(session.getRefresh_token());
				sdvUser.setExpiresIn(session.getExpires_in());
				Calendar c = Calendar.getInstance();
				sdvUser.setUpdateTime(c.getTime());
				c.add(Calendar.DAY_OF_YEAR, EXPIRE_DAYS);
				sdvUser.setExpireDate(c.getTime());
				sdvUser.setWechatAccount(code);
				sdvUserManager.updateByKey(sdvUser);
			}
			return sdvUser;
		}
		return null;
	}

	@Override
	public SdvUser refreshToken(SdvUser sdvUser) {
		String url = String.format(ApiConfig.refreshToken,
				apiConfig.getAppOpenId(), sdvUser.getRefreshToken());
		String content = WeixinUtil
				.httpRequests(url, WeixinUtil.HTTP_GET, null);
		LOG.error("weixin msg:[{}]", content);
		if (Strings.isNullOrEmpty(content)) {
			return null;
		}
		SessionBean session = JSON.parseObject(content, SessionBean.class);
		if (session != null && session.getAccess_token() != null) {
			sdvUser.setAccessToken(session.getAccess_token());
			sdvUser.setRefreshToken(session.getRefresh_token());
			sdvUser.setExpiresIn(session.getExpires_in());
			sdvUserManager.updateByKey(sdvUser);
		}
		return null;
	}

	@Override
	public String accessToken2UserInfo(String accessToken, String openId) {
		String url = String.format(ApiConfig.accessToken2UserInfo, accessToken,
				openId);
		String content = WeixinUtil
				.httpRequests(url, WeixinUtil.HTTP_GET, null);
		LOG.error("weixin msg:[{}]", content);
		if (Strings.isNullOrEmpty(content)) {
			return null;
		}
		return null;
	}

}
