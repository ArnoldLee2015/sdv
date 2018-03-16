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

import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.service.SdvTemplateVisitService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * SDV模板访视服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvTemplateVisit")
public class SdvTemplateVisitController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvTemplateVisitController.class);
	@Autowired
	private SdvTemplateVisitService sdvTemplateVisitService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvTemplateVisit(@RequestBody SdvTemplateVisit t) {
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
			sdvTemplateVisitService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvTemplateVisit error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvTemplateVisit(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvTemplateVisitService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvTemplateVisit error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvTemplateVisit> getSdvTemplateVisit(@PathVariable("id") Long id) {
		ResultMessage<SdvTemplateVisit> result = ResultMessage.success();
		result.setData(TranslationUtil.translation(sdvTemplateVisitService.selectEntry(id)));
		return result;
	}

	/**
	 * 通过条件查询访视记录列表
	 * 
	 * @param condtion
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<List<SdvTemplateVisit>> getSdvTemplateVisitList(@RequestBody SdvTemplateVisit condtion) {
		ResultMessage<List<SdvTemplateVisit>> result = ResultMessage.success();
		result.setData(TranslationUtil.translations(sdvTemplateVisitService.selectEntryList(condtion)));
		return result;
	}
}