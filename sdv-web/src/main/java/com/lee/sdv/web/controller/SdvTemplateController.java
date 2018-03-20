/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.domain.SdvTemplateData;
import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.domain.TemplateVisitData;
import com.lee.sdv.service.SdvTemplateDataService;
import com.lee.sdv.service.SdvTemplateService;
import com.lee.sdv.service.SdvTemplateVisitService;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.service.TemplateVisitDataService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * SDV模板服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvTemplate")
public class SdvTemplateController {
	private static final Logger LOG = LoggerFactory
			.getLogger(SdvTemplateController.class);
	@Autowired
	private SdvTemplateService sdvTemplateService;
	@Autowired
	private SdvTemplateDataService sdvTemplateDataService;
	@Autowired
	private SdvTemplateVisitService sdvTemplateVisitService;
	@Autowired
	private TemplateVisitDataService templateVisitDataService;
	@Autowired
	private SdvUserService sdvUserService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	@Transactional
	public ResultMessage<Long> saveSdvTemplate(@RequestBody SdvTemplate t) {
		ResultMessage<Long> result = ResultMessage.success();
		try {
			UserContext user = UserContext.getUserContext();
			Date now = new Date();
			boolean isNew = false;
			if (t.getId() == null) {
				isNew = true;
				t.setOwner(user.getId());
				t.setIsDelete(0);
				t.setCreateId(user.getId());
				t.setCreateTime(now);
			} else {
				t.setUpdateId(user.getId());
				t.setUpdateTime(now);
			}
			sdvTemplateService.saveOrUpdate(t);
			if (isNew && t.getSourceId() != null) {
				if (t.getExtendMap().get("userName") != null) {
					SdvUser sdvUser = new SdvUser();
					sdvUser.setId(user.getId());
					sdvUser.setName(t.getExtendMap().get("userName").toString());
					sdvUserService.updateByKey(sdvUser);
				}
				SdvTemplateData condtion1 = new SdvTemplateData();
				condtion1.setSdvTemplateId(t.getSourceId());
				List<SdvTemplateData> datas = sdvTemplateDataService
						.selectEntryList(condtion1);
				Map<Long, Long> dataIdMap = new HashMap<Long, Long>();
				if (!CollectionUtils.isEmpty(datas)) {
					for (SdvTemplateData data : datas) {
						Long oldId = data.getId();
						data.setId(null);
						data.setSdvTemplateId(t.getId());
						data.setCreateId(user.getId());
						data.setCreateTime(now);
						data.setUpdateId(null);
						data.setUpdateTime(null);
						sdvTemplateDataService.insertEntry(data);
						dataIdMap.put(oldId, data.getId());
					}
				}
				SdvTemplateVisit condtion2 = new SdvTemplateVisit();
				condtion2.setSdvTemplateId(t.getSourceId());
				List<SdvTemplateVisit> visits = sdvTemplateVisitService
						.selectEntryList(condtion2);
				if (!CollectionUtils.isEmpty(visits)) {
					for (SdvTemplateVisit visit : visits) {
						Long visitId= visit.getId();
						visit.setId(null);
						visit.setSdvTemplateId(t.getId());
						visit.setCreateId(user.getId());
						visit.setCreateTime(now);
						visit.setUpdateId(null);
						visit.setUpdateTime(null);
						sdvTemplateVisitService.insertEntry(visit);
						TemplateVisitData condtion3 = new TemplateVisitData();
						condtion3.setVisitId(visitId);
						List<TemplateVisitData> visitDatas = templateVisitDataService
								.selectEntryList(condtion3);
						if (!CollectionUtils.isEmpty(visitDatas)) {
							for (TemplateVisitData visitData : visitDatas) {
								visitData.setId(null);
								visitData.setVisitId(visit.getId());
								visitData.setDataId(dataIdMap.get(visitData
										.getDataId()));
								visitData.setCreateId(user.getId());
								visitData.setCreateTime(now);
								visitData.setUpdateId(null);
								visitData.setUpdateTime(null);
								templateVisitDataService.insertEntry(visitData);
							}
						}
					}
				}
			}
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
		SdvTemplate t = sdvTemplateService.selectEntry(id);
		if (t != null) {
			SdvTemplateData condtion1 = new SdvTemplateData();
			condtion1.setSdvTemplateId(t.getSourceId());
			List<SdvTemplateData> datas = sdvTemplateDataService
					.selectEntryList(condtion1);
			t.setDatas(TranslationUtil.translations(datas));
			SdvTemplateVisit condtion2 = new SdvTemplateVisit();
			condtion2.setSdvTemplateId(t.getSourceId());
			List<SdvTemplateVisit> visits = sdvTemplateVisitService
					.selectEntryList(condtion2);
			t.setVisits(TranslationUtil.translations(visits));
			if (!CollectionUtils.isEmpty(visits)) {
				List<TemplateVisitData> visitDatas = new ArrayList<TemplateVisitData>();
				for (SdvTemplateVisit visit : visits) {
					TemplateVisitData condtion3 = new TemplateVisitData();
					condtion3.setVisitId(visit.getId());
					List<TemplateVisitData> visitData = templateVisitDataService
							.selectEntryList(condtion3);
					if (!CollectionUtils.isEmpty(visitData)) {
						visitDatas.addAll(visitData);
					}
				}
				t.setVisitDatas(visitDatas);
			}
		}
		result.setData(TranslationUtil.translation(t));
		return result;
	}

	/**
	 * 查询当前用户的所有模板列表
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<List<SdvTemplate>> getAllSdvTemplate() {
		ResultMessage<List<SdvTemplate>> result = ResultMessage.success();
		SdvTemplate condtion = new SdvTemplate();
		UserContext user = UserContext.getUserContext();
		condtion.setOwner(user.getId());
		result.setData(sdvTemplateService.selectEntryList(condtion));
		return result;
	}

	/**
	 * 检查当前模板是都自己的
	 * 
	 * @param id
	 * @return true 自己的模板，否则不是
	 */
	@GetMapping("/checkOwner/{id}")
	public ResultMessage<Boolean> checkOwner(@PathVariable("id") Long id) {
		ResultMessage<Boolean> result = ResultMessage.success();
		result.setData(false);
		SdvTemplate t = sdvTemplateService.selectEntry(id);
		if (t != null
				&& t.getOwner().equals(UserContext.getUserContext().getId())) {
			result.setData(true);
		}
		return result;
	}
}