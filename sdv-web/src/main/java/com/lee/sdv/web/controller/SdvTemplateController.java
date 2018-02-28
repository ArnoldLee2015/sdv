/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.service.SdvTemplateService;
import com.lee.sdv.web.controller.domain.ResultMessage;

/**
 * SDV模板服务
 * 
 * @author lipeng
 */
@Controller
@RequestMapping(value = "/api/sdvTemplate")
public class SdvTemplateController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvTemplateController.class);
	@Autowired
	private SdvTemplateService sdvTemplateService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvTemplate(@RequestBody SdvTemplate t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			sdvTemplateService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvTemplate error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvTemplate(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvTemplateService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvTemplate error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvTemplate> getSdvTemplate(@PathVariable("id") Long id) {
		ResultMessage<SdvTemplate> result = ResultMessage.success();
		result.setData(sdvTemplateService.selectEntry(id));
		return result;
	}

	/**
	 * 查询当前用户的所有模板列表
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("")
	public ResultMessage<List<SdvTemplate>> getAllSdvTemplate() {
		ResultMessage<List<SdvTemplate>> result = ResultMessage.success();
		SdvTemplate condtion = new SdvTemplate();
		result.setData(sdvTemplateService.selectEntryList(condtion));
		return result;
	}

}