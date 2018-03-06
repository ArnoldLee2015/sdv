/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import com.lee.sdv.domain.base.BaseDomain;

import java.util.Date;

/**
 * patient_record_file 数据库表名为patient_record_file
 * @author lipeng
 */
public class PatientRecordFile extends BaseDomain {
    private static final long serialVersionUID = 1L;
    /** 系统生成开始,请勿修改,重新生成会覆盖 */
    /**
     * 患者访视记录ID
     */
    private Long patientRecordId;
    /**
     * 附件名称
     */
    private String fileName;
    /**
     * 附件类型
     */
    private String fileType;
    /**
     * 附件地址
     */
    private String fileUrl;
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

    public PatientRecordFile(){
        //默认无参构造方法
    }

    /**
     * 获取患者访视记录ID patientRecordId
     *
     * @return
     */
    public Long getPatientRecordId(){
        return patientRecordId;
    }

    /**
     * 设置患者访视记录ID patientRecordId
     *
     * @param patientRecordId 患者访视记录ID
     */
    public void setPatientRecordId (Long patientRecordId){
        this.patientRecordId = patientRecordId;
    }
    /**
     * 获取附件名称 fileName
     *
     * @return
     */
    public String getFileName(){
        return fileName;
    }

    /**
     * 设置附件名称 fileName
     *
     * @param fileName 附件名称
     */
    public void setFileName (String fileName){
        this.fileName = fileName;
    }
    /**
     * 获取附件类型 fileType
     *
     * @return
     */
    public String getFileType(){
        return fileType;
    }

    /**
     * 设置附件类型 fileType
     *
     * @param fileType 附件类型
     */
    public void setFileType (String fileType){
        this.fileType = fileType;
    }
    /**
     * 获取附件地址 fileUrl
     *
     * @return
     */
    public String getFileUrl(){
        return fileUrl;
    }

    /**
     * 设置附件地址 fileUrl
     *
     * @param fileUrl 附件地址
     */
    public void setFileUrl (String fileUrl){
        this.fileUrl = fileUrl;
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