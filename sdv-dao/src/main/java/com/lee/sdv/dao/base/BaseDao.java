/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * dao基类<实体,主键>
 * @author lipeng
 * @param <T> 实体
 * @param <KEY> 主键
 */
public interface BaseDao<T,KEY extends Serializable> {

	/**
	 * 添加对象
	 * @param t
	 * @return 影响条数
	 */
	int insertEntry(T t);
	
	/**
	 * 添加对象并设置ID到对象上(需开启事务)
	 * @param t
	 * @return 影响条数
	 */
	int insertEntryCreateId(T t);
	
	/**
	 * 删除对象,主键
	 * @param key
	 * @return 影响条数
	 */
	int deleteByKey(KEY key);
	
	/**
	 * 删除对象,条件
	 * @param condtion
	 * @return 影响条数
	 */
	int deleteByKey(T condtion);
	
	/**
	 * 更新对象,条件主键ID
	 * @param t
	 * @return 影响条数
	 */
	int updateByKey(T t);

	/**
	 * 查询对象,条件主键
	 * @param key
	 * @return
	 */
	T selectEntry(KEY key);
	
    /**
     * 通过条件查询一个对象
     * @param t
     * @return
     */
    T selectOneEntry(T t);
	
	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * @param t
	 * @return
	 */
	List<T> selectEntryList(T t);

	/**
	 * 查询对象总数
	 * @param t
	 * @return
	 */
	Integer selectEntryListCount(T t);

	Map selectOneBySql(String sql);
	Map selectOneBySql(String sql, Map params);
	List selectListBySql(String sql);
	List selectListBySql(String sql, Map params);
}
