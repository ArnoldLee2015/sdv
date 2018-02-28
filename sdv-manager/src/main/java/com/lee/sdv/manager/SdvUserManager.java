/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager;

import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.dao.SdvUserDao;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.manager.base.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lipeng
 */
@Service
public class SdvUserManager extends BaseManager<SdvUser, Long> {
    @Autowired
    private SdvUserDao sdvUserDao;

    public BaseDao<SdvUser, Long> getDao() {
        return sdvUserDao;
    }
    //自定义扩展

}