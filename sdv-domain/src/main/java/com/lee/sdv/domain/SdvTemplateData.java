/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import com.lee.sdv.domain.base.BaseDomain;

import java.util.Date;

/**
 * sdv_template_data 数据库表名为sdv_template_data
 * @author lipeng
 */
public class SdvTemplateData extends BaseDomain {
    private static final long serialVersionUID = 1L;
    /** 系统生成开始,请勿修改,重新生成会覆盖 */
    /**
     * 数据项名称
     */
    private String name;
    /**
     * 模板ID
     */
    private Long sdvTemplateId;
    /**
     * 是否删除，1删除，0未删除
     */
    private Integer isDelete;
    /**
     * 创建人
     */
    private Long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private Long updateId;
    /**
     * 修改时间
     */
    private Date updateTime;

    public SdvTemplateData(){
        //默认无参构造方法
    }

    /**
     * 获取数据项名称 name
     *
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * 设置数据项名称 name
     *
     * @param name 数据项名称
     */
    public void setName (String name){
        this.name = name;
    }
    /**
     * 获取模板ID sdvTemplateId
     *
     * @return
     */
    public Long getSdvTemplateId(){
        return sdvTemplateId;
    }

    /**
     * 设置模板ID sdvTemplateId
     *
     * @param sdvTemplateId 模板ID
     */
    public void setSdvTemplateId (Long sdvTemplateId){
        this.sdvTemplateId = sdvTemplateId;
    }
    /**
     * 获取是否删除，1删除，0未删除 isDelete
     *
     * @return
     */
    public Integer getIsDelete(){
        return isDelete;
    }

    /**
     * 设置是否删除，1删除，0未删除 isDelete
     *
     * @param isDelete 是否删除，1删除，0未删除
     */
    public void setIsDelete (Integer isDelete){
        this.isDelete = isDelete;
    }
    /**
     * 获取创建人 createId
     *
     * @return
     */
    public Long getCreateId(){
        return createId;
    }

    /**
     * 设置创建人 createId
     *
     * @param createId 创建人
     */
    public void setCreateId (Long createId){
        this.createId = createId;
    }
    /**
     * 获取创建时间 createTime
     *
     * @return
     */
    public Date getCreateTime(){
        return createTime;
    }

    /**
     * 设置创建时间 createTime
     *
     * @param createTime 创建时间
     */
    public void setCreateTime (Date createTime){
        this.createTime = createTime;
    }
    /**
     * 获取修改人 updateId
     *
     * @return
     */
    public Long getUpdateId(){
        return updateId;
    }

    /**
     * 设置修改人 updateId
     *
     * @param updateId 修改人
     */
    public void setUpdateId (Long updateId){
        this.updateId = updateId;
    }
    /**
     * 获取修改时间 updateTime
     *
     * @return
     */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
     * 设置修改时间 updateTime
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime (Date updateTime){
        this.updateTime = updateTime;
    }
    /** 系统生成结束,请勿修改,重新生成会覆盖 */

    /** 自定义开始start */

    /** 自定义结束end */
}