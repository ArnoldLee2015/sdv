/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.service;

import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.manager.SdvTemplateVisitManager;
import com.lee.sdv.manager.base.BaseManager;
import com.lee.sdv.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvTemplateVisitService extends BaseService<SdvTemplateVisit, Long> {
    @Autowired
    private SdvTemplateVisitManager sdvTemplateVisitManager;

    public BaseManager<SdvTemplateVisit, Long> getManager() {
        return sdvTemplateVisitManager;
    }
    //自定义扩展

}