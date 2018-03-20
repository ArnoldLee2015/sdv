/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.sdv.dao.SdvPatientDataDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.domain.SdvPatientData;
import com.lee.sdv.manager.base.BaseManager;

/**
 * @author lipeng
 */
@Service
public class SdvPatientDataManager extends BaseManager<SdvPatientData, Long> {
	@Autowired
	private SdvPatientDataDao sdvPatientDataDao;

	public BaseDao<SdvPatientData, Long> getDao() {
		return sdvPatientDataDao;
	}

	// 自定义扩展

	public int updateStatusByCondtion(SdvPatientData sdvPatientData) {
		return sdvPatientDataDao.updateStatusByCondtion(sdvPatientData);
	}
}