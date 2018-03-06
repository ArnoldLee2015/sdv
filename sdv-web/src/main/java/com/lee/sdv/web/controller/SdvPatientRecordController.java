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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.service.PatientRecordFileService;
import com.lee.sdv.service.SdvPatientRecordService;
import com.lee.sdv.service.SdvPatientService;
import com.lee.sdv.service.SdvTemplateVisitService;
import com.lee.sdv.service.TemplateVisitDataService;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

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
	@Autowired
	private PatientRecordFileService patientRecordFileService;
	@Autowired
	private SdvPatientService sdvPatientService;
	@Autowired
	private SdvTemplateVisitService sdvTemplateVisitService;
	@Autowired
	private TemplateVisitDataService templateVisitDataService;

	/**
	 * 新增/修改issue信息 包括描述及附件<br>
	 * 注意：附件每次都需要全量提交，保存都是先删除后添加
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	@Transactional
	public ResultMessage<Long> saveSdvPatientRecord(@RequestBody SdvPatientRecord t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			UserContext user = UserContext.getUserContext();
			Date now = new Date();
			if (t.getId() == null) {
				t.setIsDelete(0);
				t.setCreateId(user.getId());
				t.setCreateTime(now);
				t.setStatus(0);
			} else {
				t.setUpdateId(user.getId());
				t.setUpdateTime(now);
				// 删除以后附件
				PatientRecordFile condtion = new PatientRecordFile();
				condtion.setPatientRecordId(t.getId());
				patientRecordFileService.deleteByCondtion(condtion);
			}
			sdvPatientRecordService.saveOrUpdate(t);
			// 保存附件
			if (!CollectionUtils.isEmpty(t.getFiles())) {
				for (PatientRecordFile file : t.getFiles()) {
					file.setPatientRecordId(t.getId());
					file.setIsDelete(0);
					file.setCreateId(user.getId());
					file.setCreateTime(now);
					patientRecordFileService.insertEntry(file);
				}
			}
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

	/**
	 * 通过患者ID访视记录列表
	 * 
	 * @param sdvTemplateId
	 * @return
	 */
	@GetMapping("/patient/{sdvPatientId}")
	public ResultMessage<List<SdvTemplateVisit>> getSdvTemplateVisitBySdvTemplateId(@PathVariable("sdvPatientId") Long sdvPatientId) {
		SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
		if (sdvPatient == null) {
			return ResultMessage.failure("invalid param sdvPatientId");
		}
		ResultMessage<List<SdvTemplateVisit>> result = ResultMessage.success();
		SdvTemplateVisit condtion = new SdvTemplateVisit();
		condtion.setSdvTemplateId(sdvPatient.getSdvTemplateId());
		List<SdvTemplateVisit> datas = sdvTemplateVisitService.selectEntryList(condtion);
		if (!CollectionUtils.isEmpty(datas)) {
			for (SdvTemplateVisit data : datas) {
				List<SdvPatientRecord> records = sdvPatientRecordService.selectDateRecordList(sdvPatientId, data.getSdvTemplateId(),
						data.getId());
				if (!CollectionUtils.isEmpty(records)) {
					data.setAllCount(records.size());
					int endCount = 0;
					for (SdvPatientRecord record : records) {
						if (record.getStatus() != null || record.getStatus() == 1) {
							endCount++;
						}
					}
					data.setEndCount(endCount);
				} else {
					data.setEndCount(0);
					data.setAllCount(0);
				}
			}
		}
		result.setData(datas);
		return result;
	}

	/**
	 * 通过访视ID查询访视下数据项列表
	 * 
	 * @param visitId
	 * @return
	 */
	@GetMapping("/patient/{sdvPatientId}/{visitId}")
	public ResultMessage<List<SdvPatientRecord>> getTemplateVisitDataByVisitId(@PathVariable("sdvPatientId") Long sdvPatientId,
			@PathVariable("visitId") Long visitId) {
		SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
		if (sdvPatient == null) {
			return ResultMessage.failure("invalid param sdvPatientId");
		}
		ResultMessage<List<SdvPatientRecord>> result = ResultMessage.success();
		List<SdvPatientRecord> records = sdvPatientRecordService.selectDateRecordList(sdvPatientId, sdvPatient.getSdvTemplateId(), visitId);
		result.setData(records);
		return result;
	}

	/**
	 * 查询患者issue详细信息
	 * 
	 * @param sdvPatientId
	 * @param visitId
	 * @param dataId
	 * @return
	 */
	@GetMapping("/patient/{sdvPatientId}/{visitId}/{dataId}")
	public ResultMessage<List<SdvPatientRecord>> getSdvPatientRecordList(@PathVariable("sdvPatientId") Long sdvPatientId,
			@PathVariable("visitId") Long visitId, @PathVariable("dataId") Long dataId) {
		if (sdvPatientId == null || visitId == null || dataId == null) {
			return ResultMessage.failure("invalid params");
		}
		SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
		if (sdvPatient == null) {
			return ResultMessage.failure("invalid param sdvPatientId");
		}
		ResultMessage<List<SdvPatientRecord>> result = ResultMessage.success();
		SdvPatientRecord condtion = new SdvPatientRecord();
		condtion.setSdvPatientId(sdvPatientId);
		condtion.setSdvTemplateId(sdvPatient.getSdvTemplateId());
		condtion.setVisitId(visitId);
		condtion.setDataId(dataId);
		List<SdvPatientRecord> datas = sdvPatientRecordService.selectEntryList(condtion);
		if (!CollectionUtils.isEmpty(datas)) {
			for (SdvPatientRecord record : datas) {
				PatientRecordFile condtion1 = new PatientRecordFile();
				condtion1.setPatientRecordId(record.getId());
				List<PatientRecordFile> files = patientRecordFileService.selectEntryList(condtion1);
				record.setFiles(files);
			}
		}
		result.setData(datas);
		return result;
	}
}