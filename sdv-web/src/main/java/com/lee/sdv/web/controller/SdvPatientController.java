/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.domain.common.Page;
import com.lee.sdv.service.SdvPatientService;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * 患者服务
 * 
 * @author lipeng
 */
@Controller
@RequestMapping(value = "/api/sdvPatient")
public class SdvPatientController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvPatientController.class);
	@Autowired
	private SdvPatientService sdvPatientService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvPatient(@RequestBody SdvPatient t) {
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
			sdvPatientService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvPatient error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvPatient(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvPatientService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvPatient error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvPatient> getSdvPatient(@PathVariable("id") Long id) {
		ResultMessage<SdvPatient> result = ResultMessage.success();
		result.setData(sdvPatientService.selectEntry(id));
		return result;
	}

	/**
	 * 分页查询患者信息
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<Page<SdvPatient>> getSdvPatientPage(@RequestBody SdvPatient condtion) {
		ResultMessage<Page<SdvPatient>> result = ResultMessage.success();
		result.setData(sdvPatientService.selectPage(condtion));
		return result;
	}

}