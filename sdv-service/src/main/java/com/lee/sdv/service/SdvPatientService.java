/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.service;

import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.manager.SdvPatientManager;
import com.lee.sdv.manager.base.BaseManager;
import com.lee.sdv.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvPatientService extends BaseService<SdvPatient, Long> {
    @Autowired
    private SdvPatientManager sdvPatientManager;

    public BaseManager<SdvPatient, Long> getManager() {
        return sdvPatientManager;
    }
    //自定义扩展

}