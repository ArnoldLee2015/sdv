/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.manager.base;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lee.sdv.common.exception.AppException;
import com.lee.sdv.dao.base.BaseDao;
import com.lee.sdv.domain.base.BaseDomain;
import com.lee.sdv.domain.common.Page;

/**
 * manager实现类
 * 
 * @author lipeng
 * @param <T>
 *            实体
 * @param <KEY>
 *            主键
 */
public abstract class BaseManager<T extends BaseDomain, KEY extends Serializable> {
	protected static final Logger LOG = LoggerFactory.getLogger(BaseManager.class);

	/**
	 * 获取DAO操作类
	 */
	public abstract BaseDao<T, KEY> getDao();

	/**
	 * 添加对象
	 * 
	 * @param t
	 * @return
	 */
	public int insertEntry(T t) {
		return getDao().insertEntry(t);
	}

	/**
	 * 添加对象并且设置主键ID值(需要事务支持)
	 * 
	 * @param t
	 * @return
	 */
	public int insertEntryCreateId(T t) {
		return getDao().insertEntryCreateId(t);
	}

	/**
	 * 删除对象,主键
	 * 
	 * @param key
	 *            主键数组
	 * @return 影响条数
	 */
	public int deleteByKey(KEY key) {
		return getDao().deleteByKey(key);
	}

	/**
	 * 按条件删除对象
	 * 
	 * @param condtion
	 * @return 影响条数
	 */
	public int deleteByCondtion(T condtion) {
		return getDao().deleteByCondtion(condtion);
	}

	/**
	 * 更新对象,条件主键Id
	 * 
	 * @param condtion
	 *            更新对象
	 * @return 影响条数
	 */
	public int updateByKey(T condtion) {
		return getDao().updateByKey(condtion);
	}

	/**
	 * 保存或更新对象(条件主键Id)
	 * 
	 * @param t
	 *            需更新的对象
	 * @return 影响条数
	 */
	public int saveOrUpdate(T t) {
		Long id = 0L;
		try {
			Class<?> clz = t.getClass();
			id = (Long) clz.getMethod("getId").invoke(t);
		} catch (Exception e) {
			LOG.warn("获取对象主键值失败!");
		}

		int result = 0;
		String errorInfo = "保存对象失败!";
		if (id != null && id > 0) {
			result = this.updateByKey(t);
			errorInfo = "修改对象成功!";
		} else {
			result = this.insertEntry(t);
		}
		if (result != 1) {
			errorInfo += JSON.toJSONString(t);
			LOG.error(errorInfo);
			throw new AppException(errorInfo);
		}
		return result;
	}

	/**
	 * 查询对象,条件主键
	 * 
	 * @param key
	 * @return 实体对象
	 */
	public T selectEntry(KEY key) {
		return getDao().selectEntry(key);
	}

	/**
	 * 查询对象,只要不为NULL与空则为条件
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 对象列表
	 */
	public List<T> selectEntryList(T condtion) {
		return getDao().selectEntryList(condtion);
	}

	/**
	 * 查询对象总数
	 * 
	 * @param condtion
	 * @return
	 */
	public Integer selectEntryListCount(T condtion) {
		return getDao().selectEntryListCount(condtion);
	}

	/**
	 * 分页查询
	 * 
	 * @param condtion
	 *            查询条件
	 * @return 分页对象
	 */
	public Page<T> selectPage(T condtion) {
		Page<T> page = new Page<T>();
		/*
		 * try { Class<?> clz = condtion.getClass();
		 * clz.getMethod("setStartIndex", Integer.class).invoke(condtion,
		 * page.getStartIndex()); clz.getMethod("setEndIndex",
		 * Integer.class).invoke(condtion, page.getEndIndex()); } catch
		 * (Exception e) { throw new AppException("设置分页参数失败", e); }
		 */
		if (condtion.getStartIndex() == null) {
			condtion.setStartIndex(0);
		}
		if (condtion.getEndIndex() == null) {
			condtion.setEndIndex(10);
		}
		Integer size = getDao().selectEntryListCount(condtion);
		if (size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(this.selectEntryList(condtion));
		return page;
	}

	/**
	 * 选择第一条数据
	 * 
	 * @param condtion
	 * @return
	 */
	public T selectOneEntry(T condtion) {
		return getDao().selectOneEntry(condtion);
	}
}
