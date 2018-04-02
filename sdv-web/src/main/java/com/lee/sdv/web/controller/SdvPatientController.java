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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.domain.common.Page;
import com.lee.sdv.service.SdvPatientRecordService;
import com.lee.sdv.service.SdvPatientService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * 患者服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvPatient")
public class SdvPatientController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvPatientController.class);
	@Autowired
	private SdvPatientService sdvPatientService;
	@Autowired
	private SdvPatientRecordService sdvPatientRecordService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	@Transactional
	public ResultMessage<Long> saveSdvPatient(@RequestBody SdvPatient t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			UserContext user = UserContext.getUserContext();
			SdvPatient condtion1 = new SdvPatient();
			condtion1.setPatientNo(t.getPatientNo());
			condtion1.setSdvTemplateId(t.getSdvTemplateId());
			List<SdvPatient> temp = sdvPatientService.selectEntryList(condtion1);
			if (t.getId() == null) {
				if (!CollectionUtils.isEmpty(temp)) {
					return ResultMessage.failure("患者已存在");
				}
				t.setStatus(0);
				t.setIsDelete(0);
				t.setCreateId(user.getId());
				t.setCreateTime(new Date());
			} else {
				if (!CollectionUtils.isEmpty(temp) && !temp.get(0).getId().equals(t.getId())) {
					return ResultMessage.failure("患者已存在");
				}
				t.setUpdateId(user.getId());
				t.setUpdateTime(new Date());
				SdvPatient old = sdvPatientService.selectEntry(t.getId());
				// 修改 了患者模板则同时删除之前模板的记录
				if (old != null && old.getSdvTemplateId() != null && !old.getSdvTemplateId().equals(t.getSdvTemplateId())) {
					SdvPatientRecord condtion = new SdvPatientRecord();
					condtion.setSdvPatientId(t.getId());
					condtion.setSdvTemplateId(old.getSdvTemplateId());
					sdvPatientRecordService.deleteByCondtion(condtion);
				}
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
	 * @param condtion
	 *            多个模板id入参格式：{"extendMap":{"sdvTemplateIds":[1,2,3]}}
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<Page<SdvPatient>> getSdvPatientPage(@RequestBody SdvPatient condtion) {
		ResultMessage<Page<SdvPatient>> result = ResultMessage.success();
		UserContext user = UserContext.getUserContext();
		if (user != null) {
			condtion.setCreateId(user.getId());
		}
		Page<SdvPatient> page = sdvPatientService.selectPage(condtion);
		TranslationUtil.translations(page.getResult());
		result.setData(page);
		return result;
	}

}