/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.dao;

import java.util.List;

import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.dao.base.BaseDao;

/**
 * @author lipeng
 */
public interface SdvPatientRecordDao extends BaseDao<SdvPatientRecord, Long> {
	// 自定义扩展
	public List<SdvPatientRecord> selectDateRecordList(SdvPatientRecord sdvPatientRecord);
}