/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.dao.SdvUserDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvUserManager extends BaseManager<SdvUser, Long> {
	@Autowired
	private SdvUserDao sdvUserDao;

	public BaseDao<SdvUser, Long> getDao() {
		return sdvUserDao;
	}

	// 自定义扩展

	/**
	 * 通过系统会话key获取当前用户
	 * 
	 * @param localSessionKey
	 * @return
	 */
	public SdvUser selectByLocalSessionKey(String localSessionKey) {
		SdvUser t = new SdvUser();
		t.setIsDelete(0);
		t.setLocalSessionKey(localSessionKey);
		return sdvUserDao.selectOneEntry(t);
	}

	/**
	 * 通过微信用户ID获取当前用户
	 * 
	 * @param openId
	 * @return
	 */
	public SdvUser selectByOpenId(String openId) {
		SdvUser t = new SdvUser();
		t.setIsDelete(0);
		t.setOpenId(openId);
		return sdvUserDao.selectOneEntry(t);
	}
}