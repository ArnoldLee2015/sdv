/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.dao.PatientRecordFileDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class PatientRecordFileManager extends BaseManager<PatientRecordFile, Long> {
    @Autowired
    private PatientRecordFileDao patientRecordFileDao;

    public BaseDao<PatientRecordFile, Long> getDao() {
        return patientRecordFileDao;
    }
    //自定义扩展

}