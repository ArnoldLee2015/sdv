/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.sdv.domain.SdvPatientData;
import com.lee.sdv.manager.SdvPatientDataManager;
import com.lee.sdv.manager.base.BaseManager;
import com.lee.sdv.service.base.BaseService;

/**
 * @author lipeng
 */
@Service
public class SdvPatientDataService extends BaseService<SdvPatientData, Long> {
	@Autowired
	private SdvPatientDataManager sdvPatientDataManager;

	public BaseManager<SdvPatientData, Long> getManager() {
		return sdvPatientDataManager;
	}

	// 自定义扩展

	public int updateStatusByCondtion(SdvPatientData sdvPatientData) {
		return sdvPatientDataManager.updateStatusByCondtion(sdvPatientData);
	}
}