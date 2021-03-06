/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.sdv.domain.SdvTemplateData;
import com.lee.sdv.service.SdvTemplateDataService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * SDV模板数据项服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvTemplateData")
public class SdvTemplateDataController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvTemplateDataController.class);
	@Autowired
	private SdvTemplateDataService sdvTemplateDataService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvTemplateData(@RequestBody SdvTemplateData t) {
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
			sdvTemplateDataService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvTemplateData error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvTemplateData(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvTemplateDataService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvTemplateData error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvTemplateData> getSdvTemplateData(@PathVariable("id") Long id) {
		ResultMessage<SdvTemplateData> result = ResultMessage.success();
		result.setData(TranslationUtil.translation(sdvTemplateDataService.selectEntry(id)));
		return result;
	}

	/**
	 * 通过条件查询数据项列表
	 * 
	 * @param condtion
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<List<SdvTemplateData>> getSdvTemplateDataList(@RequestBody SdvTemplateData condtion) {
		ResultMessage<List<SdvTemplateData>> result = ResultMessage.success();
		result.setData(TranslationUtil.translations(sdvTemplateDataService.selectEntryList(condtion)));
		return result;
	}
}