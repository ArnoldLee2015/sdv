/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.dao.SdvPatientRecordDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvPatientRecordManager extends BaseManager<SdvPatientRecord, Long> {
    @Autowired
    private SdvPatientRecordDao sdvPatientRecordDao;

    public BaseDao<SdvPatientRecord, Long> getDao() {
        return sdvPatientRecordDao;
    }
    //自定义扩展

}