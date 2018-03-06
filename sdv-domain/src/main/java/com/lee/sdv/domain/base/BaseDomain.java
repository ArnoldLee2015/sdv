/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.domain.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 领域模型基类(常规公共字段)<br/>
 * 一律使用引用类型
 * 
 * @author lipeng
 */
public class BaseDomain extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	private Map<String, Object> extendMap = new HashMap<String, Object>();// 扩展字段,一般用于查询

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
	 * @param id
	 *            主键
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param extendMap
	 *            拓展字段map
	 */
	public void setExtendMap(Map<String, Object> extendMap) {
		this.extendMap = extendMap;
	}
}
