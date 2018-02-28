/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

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

import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.service.SdvPatientRecordService;
import com.lee.sdv.web.controller.domain.ResultMessage;

/**
 * 患者记录信息服务
 * 
 * @author lipeng
 */
@Controller
@RequestMapping(value = "/api/sdvPatientRecord")
public class SdvPatientRecordController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvPatientRecordController.class);
	@Autowired
	private SdvPatientRecordService sdvPatientRecordService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvPatientRecord(@RequestBody SdvPatientRecord t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			sdvPatientRecordService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvPatientRecord error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvPatientRecord(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvPatientRecordService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvPatientRecord error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvPatientRecord> getSdvPatientRecord(@PathVariable("id") Long id) {
		ResultMessage<SdvPatientRecord> result = ResultMessage.success();
		result.setData(sdvPatientRecordService.selectEntry(id));
		return result;
	}
}