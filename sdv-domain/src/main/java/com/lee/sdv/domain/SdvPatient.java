/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import com.lee.sdv.domain.base.BaseDomain;

import java.util.Date;

/**
 * sdv_patient 数据库表名为sdv_patient
 * @author lipeng
 */
public class SdvPatient extends BaseDomain {
    private static final long serialVersionUID = 1L;
    /** 系统生成开始,请勿修改,重新生成会覆盖 */
    /**
     * 患者编码
     */
    private String patientNo;
    /**
     * 患者姓名
     */
    private String patientName;
    /**
     * 关联模板ID
     */
    private Long sdvTemplateId;
    /**
     * 访视状态，0未开始，1进行中，2完成
     */
    private Integer status;
    /**
     * 是否删除，1删除，0未删除
     */
    private Integer isDelete=0;
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

    public SdvPatient(){
        //默认无参构造方法
    }

    /**
     * 获取患者编码 patientNo
     *
     * @return
     */
    public String getPatientNo(){
        return patientNo;
    }

    /**
     * 设置患者编码 patientNo
     *
     * @param patientNo 患者编码
     */
    public void setPatientNo (String patientNo){
        this.patientNo = patientNo;
    }
    /**
     * 获取患者姓名 patientName
     *
     * @return
     */
    public String getPatientName(){
        return patientName;
    }

    /**
     * 设置患者姓名 patientName
     *
     * @param patientName 患者姓名
     */
    public void setPatientName (String patientName){
        this.patientName = patientName;
    }
    /**
     * 获取关联模板ID sdvTemplateId
     *
     * @return
     */
    public Long getSdvTemplateId(){
        return sdvTemplateId;
    }

    /**
     * 设置关联模板ID sdvTemplateId
     *
     * @param sdvTemplateId 关联模板ID
     */
    public void setSdvTemplateId (Long sdvTemplateId){
        this.sdvTemplateId = sdvTemplateId;
    }
    /**
     * 获取访视状态，0未开始，1进行中，2完成 status
     *
     * @return
     */
    public Integer getStatus(){
        return status;
    }

    /**
     * 设置访视状态，0未开始，1进行中，2完成 status
     *
     * @param status 访视状态，0未开始，1进行中，2完成
     */
    public void setStatus (Integer status){
        this.status = status;
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