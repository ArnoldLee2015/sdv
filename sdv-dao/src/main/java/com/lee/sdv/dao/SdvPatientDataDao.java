/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.dao;

import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.domain.SdvPatientData;

/**
 * @author lipeng
 */
public interface SdvPatientDataDao extends BaseDao<SdvPatientData, Long> {
	// 自定义扩展
	public int updateStatusByCondtion(SdvPatientData sdvPatientData);
}