/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 领域模型基类(常规公共字段)<br/>
 * 一律使用引用类型
 * @author lipeng
 */
public class BaseDomain extends BaseQuery {
    /**
     * 主键
     */
    private Long id;
    /**
     * 是否有效
     */
    private Integer yn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date modifiedDate;
    /**
     * 最后修改者
     */
    private String modifiedUser;


    private Map<String, Object> extendMap = new HashMap<String, Object>();//扩展字段,一般用于查询


    /**
     * 获取主键 id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键 id
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 获取是否有效 yn
     *
     * @return
     */
    public Integer getYn() {
        return yn;
    }

    /**
     * 设置是否有效 yn
     *
     * @param yn 是否有效
     */
    public void setYn(Integer yn) {
        this.yn = yn;
    }

    /**
     * 获取备注 remark
     *
     * @return
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注 remark
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建时间 createdDate
     *
     * @return
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 设置创建时间 createdDate
     *
     * @param createdDate 创建时间
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 获取更新时间 modifiedDate
     *
     * @return
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * 设置更新时间 modifiedDate
     *
     * @param modifiedDate 更新时间
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    /**
     * 获取最后修改者 modifiedUser
     *
     * @return
     */
    public String getModifiedUser() {
        return modifiedUser;
    }

    /**
     * 设置更新时间 modifiedUser
     *
     * @param modifiedUser 最后修改者
     */
    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * 获取拓展extendMap extendMap
     *
     * @return
     */
    public Map<String, Object> getExtendMap() {
        return extendMap;
    }


    /**
     * 设置拓展extendMap extendMap
     *
     * @param extendMap 拓展字段map
     */
    public void setExtendMap(Map<String, Object> extendMap) {
        this.extendMap = extendMap;
    }
}
