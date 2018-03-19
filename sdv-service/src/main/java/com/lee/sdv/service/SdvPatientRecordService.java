/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.manager.SdvPatientRecordManager;
import com.lee.sdv.manager.base.BaseManager;
import com.lee.sdv.service.base.BaseService;

/**
 * @author lipeng
 */
@Service
public class SdvPatientRecordService extends BaseService<SdvPatientRecord, Long> {
	@Autowired
	private SdvPatientRecordManager sdvPatientRecordManager;

	public BaseManager<SdvPatientRecord, Long> getManager() {
		return sdvPatientRecordManager;
	}

	// 自定义扩展
	/**
	 * 查询患者访视记录下数据项状态
	 * 
	 * @param patientId
	 * @param templateId
	 * @param visitId
	 * @return
	 */
	public List<SdvPatientRecord> selectDateRecordList(Long patientId, Long templateId, Long visitId) {
		return sdvPatientRecordManager.selectDateRecordList(patientId, templateId, visitId);
	}
	
	public int updateStatusByCondtion(SdvPatientRecord sdvPatientRecord){
		return sdvPatientRecordManager.updateStatusByCondtion(sdvPatientRecord);
	}
}