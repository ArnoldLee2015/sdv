/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import java.util.Date;
import java.util.List;

import com.lee.sdv.domain.base.BaseDomain;
import com.lee.sdv.translation.Translation;

/**
 * 模板表 数据库表名为sdv_template
 * 
 * @author lipeng
 */
public class SdvTemplate extends BaseDomain {
	private static final long serialVersionUID = 1L;
	/** 系统生成开始,请勿修改,重新生成会覆盖 */
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 模板拥有人
	 */
	private Long owner;
	/**
	 * 模板来源ID
	 */
	private Long sourceId;
	/**
	 * 模板简介
	 */
	private String remark;
	/**
	 * 是否删除，1删除，0未删除
	 */
	private Integer isDelete = 0;
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

	public SdvTemplate() {
		// 默认无参构造方法
	}

	/**
	 * 获取模板名称 name
	 *
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置模板名称 name
	 *
	 * @param name
	 *            模板名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取模板拥有人 owner
	 *
	 * @return
	 */
	public Long getOwner() {
		return owner;
	}

	/**
	 * 设置模板拥有人 owner
	 *
	 * @param owner
	 *            模板拥有人
	 */
	public void setOwner(Long owner) {
		this.owner = owner;
	}

	/**
	 * 获取模板来源ID sourceId
	 *
	 * @return
	 */
	public Long getSourceId() {
		return sourceId;
	}

	/**
	 * 设置模板来源ID sourceId
	 *
	 * @param sourceId
	 *            模板来源ID
	 */
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取是否删除，1删除，0未删除 isDelete
	 *
	 * @return
	 */
	public Integer getIsDelete() {
		return isDelete;
	}

	/**
	 * 设置是否删除，1删除，0未删除 isDelete
	 *
	 * @param isDelete
	 *            是否删除，1删除，0未删除
	 */
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * 获取创建人 createId
	 *
	 * @return
	 */
	public Long getCreateId() {
		return createId;
	}

	/**
	 * 设置创建人 createId
	 *
	 * @param createId
	 *            创建人
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	/**
	 * 获取创建时间 createTime
	 *
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间 createTime
	 *
	 * @param createTime
	 *            创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取修改人 updateId
	 *
	 * @return
	 */
	public Long getUpdateId() {
		return updateId;
	}

	/**
	 * 设置修改人 updateId
	 *
	 * @param updateId
	 *            修改人
	 */
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	/**
	 * 获取修改时间 updateTime
	 *
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设置修改时间 updateTime
	 *
	 * @param updateTime
	 *            修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/** 系统生成结束,请勿修改,重新生成会覆盖 */

	/** 自定义开始start */

	/**
	 * 模板拥有人昵称
	 */
	@Translation(source = "owner", type = "ownerName")
	private String ownerName;
	/**
	 * 模板访视记录列表
	 */
	private List<SdvTemplateVisit> visits;
	/**
	 * 模板数据项列表
	 */
	private List<SdvTemplateData> datas;
	/**
	 * 模板访视记录与数据项关系表
	 */
	private List<TemplateVisitData> visitDatas;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<SdvTemplateVisit> getVisits() {
		return visits;
	}

	public void setVisits(List<SdvTemplateVisit> visits) {
		this.visits = visits;
	}

	public List<SdvTemplateData> getDatas() {
		return datas;
	}

	public void setDatas(List<SdvTemplateData> datas) {
		this.datas = datas;
	}

	public List<TemplateVisitData> getVisitDatas() {
		return visitDatas;
	}

	public void setVisitDatas(List<TemplateVisitData> visitDatas) {
		this.visitDatas = visitDatas;
	}

	/** 自定义结束end */
}