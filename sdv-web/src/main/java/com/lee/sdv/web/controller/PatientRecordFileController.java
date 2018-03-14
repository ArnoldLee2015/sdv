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

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.service.PatientRecordFileService;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * 患者记录附件服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/api/patientRecordFile")
public class PatientRecordFileController {
	private static final Logger LOG = LoggerFactory.getLogger(PatientRecordFileController.class);
	@Autowired
	private PatientRecordFileService patientRecordFileService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> savePatientRecordFile(@RequestBody PatientRecordFile t) {
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
			patientRecordFileService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("savePatientRecordFile error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deletePatientRecordFile(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(patientRecordFileService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deletePatientRecordFile error[{}]", e.getMessage(), e);
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
	public ResultMessage<PatientRecordFile> getPatientRecordFile(@PathVariable("id") Long id) {
		ResultMessage<PatientRecordFile> result = ResultMessage.success();
		result.setData(patientRecordFileService.selectEntry(id));
		return result;
	}

	/**
	 * 通过患者记录id查询记录下所有附件信息
	 * 
	 * @param recordId
	 * @return
	 */
	@PostMapping("/record/{id}")
	public ResultMessage<List<PatientRecordFile>> getPatientRecordFileByRecordId(@PathVariable("id") Long recordId) {
		ResultMessage<List<PatientRecordFile>> result = ResultMessage.success();
		PatientRecordFile condtion = new PatientRecordFile();
		condtion.setPatientRecordId(recordId);
		result.setData(patientRecordFileService.selectEntryList(condtion));
		return result;
	}

}