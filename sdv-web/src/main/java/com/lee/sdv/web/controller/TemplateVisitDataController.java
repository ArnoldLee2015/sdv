/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.sdv.domain.TemplateVisitData;
import com.lee.sdv.service.TemplateVisitDataService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * SDV模板访视与数据项关系服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/templateVisitData")
public class TemplateVisitDataController {
	private static final Logger LOG = LoggerFactory.getLogger(TemplateVisitDataController.class);
	@Autowired
	private TemplateVisitDataService templateVisitDataService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveTemplateVisitData(@RequestBody TemplateVisitData t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			UserContext user = UserContext.getUserContext();
			if (t.getId() == null) {
				t.setIsDelete(0);
				t.setCreateId(user.getId());
				t.setCreateTime(new Date());
			} else {
				t.setUpdateId(user.getId());
				t.setUpdateTime(new Date());
			}
			templateVisitDataService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveTemplateVisitData error[{}]", e.getMessage(), e);
			result = ResultMessage.failure("系统错误");
		}
		return result;
	}

	/**
	 * 通过id删除信息
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResultMessage<Integer> deleteTemplateVisitData(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(templateVisitDataService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteTemplateVisitData error[{}]", e.getMessage(), e);
			result = ResultMessage.failure("系统错误");
		}
		return result;
	}

	/**
	 * 通过id查询单个对象信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResultMessage<TemplateVisitData> getTemplateVisitData(@PathVariable("id") Long id) {
		ResultMessage<TemplateVisitData> result = ResultMessage.success();
		result.setData(TranslationUtil.translation(templateVisitDataService.selectEntry(id)));
		return result;
	}

}