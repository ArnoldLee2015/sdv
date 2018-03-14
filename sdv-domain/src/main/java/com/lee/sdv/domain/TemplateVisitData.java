/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain;

import java.util.Date;

import com.lee.sdv.domain.base.BaseDomain;
import com.lee.sdv.translation.Translation;

/**
 * 访视数据项关系表 数据库表名为template_visit_data
 * 
 * @author lipeng
 */
public class TemplateVisitData extends BaseDomain {
	private static final long serialVersionUID = 1L;
	/** 系统生成开始,请勿修改,重新生成会覆盖 */
	/**
	 * 访视ID
	 */
	private Long visitId;
	/**
	 * 数据项ID
	 */
	private Long dataId;

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

	public TemplateVisitData() {
		// 默认无参构造方法
	}

	/**
	 * 获取访视ID visitId
	 *
	 * @return
	 */
	public Long getVisitId() {
		return visitId;
	}

	/**
	 * 设置访视ID visitId
	 *
	 * @param visitId
	 *            访视ID
	 */
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	/**
	 * 获取数据项ID dataId
	 *
	 * @return
	 */
	public Long getDataId() {
		return dataId;
	}

	/**
	 * 设置数据项ID dataId
	 *
	 * @param dataId
	 *            数据项ID
	 */
	public void setDataId(Long dataId) {
		this.dataId = dataId;
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
	@Translation(source = "visitId", type = "visitName")
	private String visitName;
	@Translation(source = "dataId", type = "dataName")
	private String dataName;

	public String getVisitName() {
		return visitName;
	}

	public void setVisitName(String visitName) {
		this.visitName = visitName;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/** 自定义结束end */
}