/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.dao.SdvTemplateVisitDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvTemplateVisitManager extends BaseManager<SdvTemplateVisit, Long> {
    @Autowired
    private SdvTemplateVisitDao sdvTemplateVisitDao;

    public BaseDao<SdvTemplateVisit, Long> getDao() {
        return sdvTemplateVisitDao;
    }
    //自定义扩展

}