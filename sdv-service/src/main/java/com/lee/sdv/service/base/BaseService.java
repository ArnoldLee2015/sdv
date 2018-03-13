/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.service.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lee.sdv.domain.base.BaseDomain;
import com.lee.sdv.domain.common.Page;
import com.lee.sdv.manager.base.BaseManager;

/**
 * service实现类
 * 
 * @param <T>
 *            实体
 * @param <KEY>
 *            主键
 * @author shiling
 */
public abstract class BaseService<T extends BaseDomain, KEY extends Serializable> {
	protected static final Logger LOG = LoggerFactory.getLogger(BaseService.class);

	/**
	 * 获取manager操作类
	 */
	public abstract BaseManager<T, KEY> getManager();

	/**
	 * 添加对象
	 * 
	 * @param t
	 * @return
	 */
	public int insertEntry(T t) {
		return getManager().insertEntry(t);
	}

	/**
	 * 删除对象,主键
	 * 
	 * @param key
	 *            主键数组
	 * @return 影响条数
	 */
	public int deleteByKey(KEY key) {
		return getManager().deleteByKey(key);
	}

	/**
	 * 按条件删除对象
	 * 
	 * @param condtion
	 * @return 影响条数
	 */
	public int deleteByCondtion(T condtion) {
		return getManager().deleteByCondtion(condtion);
	}

	/**
	 * 更新对象,条件主键Id
	 * 
	 * @param condtion
	 *            更新对象
	 * @return 影响条数
	 */
	public int updateByKey(T condtion) {
		return getManager().updateByKey(condtion);
	}

	/**
	 * 保存或更新对象(条件主键Id)
	 * 
	 * @param t
	 *            需更新的对象
	 * @return 影响条数
	 */
	public int saveOrUpdate(T t) throws Exception {
		return getManager().saveOrUpdate(t);
	}

	/**
	 * 查询对象,条件主键
	 * 
	 * @param key
	 * @return 实体对象
	 */
	public T selectEntry(KEY key) {
		return getManager().selectEntry(key);
	}

	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 对象列表
	 */
	public List<T> selectEntryList(T condtion) {
		return getManager().selectEntryList(condtion);
	}

	/**
	 * 分页查询
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 分页对象
	 */
	public Page<T> selectPage(T condtion) {
		return getManager().selectPage(condtion);
	}

	/**
	 * 选择第一条数据
	 * 
	 * @param condtion
	 * @return
	 */
	public T selectOneEntry(T condtion) {
		return getManager().selectOneEntry(condtion);
	}
}
