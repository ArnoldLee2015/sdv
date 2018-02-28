/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.dao.SdvTemplateDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvTemplateManager extends BaseManager<SdvTemplate, Long> {
    @Autowired
    private SdvTemplateDao sdvTemplateDao;

    public BaseDao<SdvTemplate, Long> getDao() {
        return sdvTemplateDao;
    }
    //自定义扩展

}