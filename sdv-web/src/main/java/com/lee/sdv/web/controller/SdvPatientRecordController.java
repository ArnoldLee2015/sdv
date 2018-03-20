/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.domain.SdvPatient;
import com.lee.sdv.domain.SdvPatientData;
import com.lee.sdv.domain.SdvPatientRecord;
import com.lee.sdv.domain.SdvTemplate;
import com.lee.sdv.domain.SdvTemplateData;
import com.lee.sdv.domain.SdvTemplateVisit;
import com.lee.sdv.domain.TemplateVisitData;
import com.lee.sdv.service.PatientRecordFileService;
import com.lee.sdv.service.SdvPatientDataService;
import com.lee.sdv.service.SdvPatientRecordService;
import com.lee.sdv.service.SdvPatientService;
import com.lee.sdv.service.SdvTemplateDataService;
import com.lee.sdv.service.SdvTemplateService;
import com.lee.sdv.service.SdvTemplateVisitService;
import com.lee.sdv.service.TemplateVisitDataService;
import com.lee.sdv.service.cos.COSClientService;
import com.lee.sdv.service.cos.domain.COSClientConfig;
import com.lee.sdv.service.cos.domain.COSResult;
import com.lee.sdv.service.mail.EmailService;
import com.lee.sdv.translation.TranslationUtil;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.dto.ExportDateRecordDto;
import com.lee.sdv.web.controller.interceptor.UserContext;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 患者记录信息服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvPatientRecord")
public class SdvPatientRecordController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvPatientRecordController.class);
	@Autowired
	private SdvPatientDataService sdvPatientDataService;
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
	@Autowired
	private SdvTemplateService sdvTemplateService;
	@Autowired
	private SdvTemplateDataService sdvTemplateDataService;
	@Autowired
	private COSClientService cosClientService;
	@Autowired
	private COSClientConfig cosClientConfig;
	@Autowired
	private EmailService emailService;

	@Value("${mail.weixinAccount}")
	private String weixinAccount;

	/**
	 * 新增/修改issue信息 包括描述及附件<br>
	 * 注意：附件每次都需要全量提交，保存都是先删除后添加
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	@Transactional
	public ResultMessage<Integer> saveSdvPatientRecord(@RequestBody ArrayList<SdvPatientRecord> list) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			UserContext user = UserContext.getUserContext();
			Date now = new Date();
			if (!CollectionUtils.isEmpty(list)) {
				Long sdvPatientId = null;
				SdvPatient sdvPatient = null;
				for (SdvPatientRecord t : list) {
					sdvPatientId = t.getSdvPatientId();
					if (sdvPatientId == null) {
						return ResultMessage.failure("invalid params");
					}
					if (sdvPatientId != null && sdvPatient == null) {
						sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
						if (sdvPatient == null) {
							return ResultMessage.failure("invalid param sdvPatientId");
						}
					}
					if (sdvPatient != null) {
						t.setSdvTemplateId(sdvPatient.getSdvTemplateId());
					}

					if (t.getId() == null) {
						SdvPatientData sdvPatientData = new SdvPatientData();
						sdvPatientData.setSdvPatientId(sdvPatientId);
						sdvPatientData.setSdvTemplateId(sdvPatient.getSdvTemplateId());
						sdvPatientData.setVisitId(t.getVisitId());
						sdvPatientData.setDataId(t.getDataId());
						SdvPatientData data = sdvPatientDataService.selectOneEntry(sdvPatientData);
						if (data == null) {// 第一次保存时插入数据项
							sdvPatientData.setCreateId(user.getId());
							sdvPatientData.setCreateTime(now);
							sdvPatientData.setUpdateId(user.getId());
							sdvPatientData.setUpdateTime(now);
							sdvPatientData.setStatus(0);
							sdvPatientDataService.insertEntry(sdvPatientData);
							t.setSdvPatientDataId(sdvPatientData.getId());
						} else {
							data.setUpdateId(user.getId());
							data.setUpdateTime(now);
							sdvPatientDataService.updateByKey(data);
							t.setSdvPatientDataId(data.getId());
						}
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
						// 更新数据项修改时间
						SdvPatientData sdvPatientData = new SdvPatientData();
						sdvPatientData.setId(t.getSdvPatientDataId());
						sdvPatientData.setUpdateId(user.getId());
						sdvPatientData.setUpdateTime(now);
						sdvPatientDataService.updateByKey(sdvPatientData);
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
				}
				if (sdvPatient.getStatus() == null || sdvPatient.getStatus() == 0) {
					sdvPatient.setStatus(1);
					sdvPatientService.updateByKey(sdvPatient);
				}
				result.setData(list.size());
			}
		} catch (Exception e) {
			LOG.error("saveSdvPatientRecord error[{}]", e.getMessage(), e);
			result = ResultMessage.failure("系统错误");
		}
		return result;
	}

	@PutMapping("/status/{sdvPatientId}/{visitId}/{dataId}")
	@Transactional
	public ResultMessage<Integer> updateStatus(@PathVariable("sdvPatientId") Long sdvPatientId, @PathVariable("visitId") Long visitId,
			@PathVariable("dataId") Long dataId) {
		ResultMessage<Integer> result = ResultMessage.success();
		UserContext user = UserContext.getUserContext();
		try {
			if (sdvPatientId == null || visitId == null || dataId == null) {
				return ResultMessage.failure("invalid params");
			}
			SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
			if (sdvPatient == null) {
				return ResultMessage.failure("invalid param sdvPatientId");
			}
			SdvPatientData sdvPatientData = new SdvPatientData();
			sdvPatientData.setSdvPatientId(sdvPatientId);
			sdvPatientData.setSdvTemplateId(sdvPatient.getSdvTemplateId());
			sdvPatientData.setVisitId(visitId);
			sdvPatientData.setDataId(dataId);
			int count = sdvPatientDataService.updateStatusByCondtion(sdvPatientData);
			result.setData(count);
			Date now = new Date();
			if (count == 0) {
				sdvPatientData.setStatus(1);
				sdvPatientData.setEndTime(now);
				sdvPatientData.setCreateTime(now);
				sdvPatientData.setCreateId(user.getId());
				sdvPatientData.setUpdateId(user.getId());
				sdvPatientData.setUpdateTime(now);
				sdvPatientDataService.insertEntry(sdvPatientData);
			}
			// 判断整体状态
			ResultMessage<List<SdvTemplateVisit>> visits = this.getSdvTemplateVisitBySdvTemplateId(sdvPatientId);
			if (!CollectionUtils.isEmpty(visits.getData())) {
				boolean isOver = true;
				for (SdvTemplateVisit visit : visits.getData()) {
					if (visit.getAllCount() != null && !visit.getAllCount().equals(visit.getEndCount())) {
						isOver = false;
						break;
					}
				}
				if (isOver) {
					sdvPatient.setUpdateId(user.getId());
					sdvPatient.setUpdateTime(now);
					sdvPatient.setStatus(2);
					sdvPatientService.updateByKey(sdvPatient);
				} else {
					sdvPatient.setUpdateId(user.getId());
					sdvPatient.setUpdateTime(now);
					sdvPatient.setStatus(1);
					sdvPatientService.updateByKey(sdvPatient);
				}
			}
		} catch (Exception e) {
			LOG.error("updateStatus error[{}]", e.getMessage(), e);
			result = ResultMessage.failure("系统错误");
		}
		return result;
	}

	@PutMapping("/delete/{sdvPatientId}/{visitId}/{dataId}")
	@Transactional
	public ResultMessage<Integer> deleteByDataId(@PathVariable("sdvPatientId") Long sdvPatientId, @PathVariable("visitId") Long visitId,
			@PathVariable("dataId") Long dataId) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			if (sdvPatientId == null || visitId == null || dataId == null) {
				return ResultMessage.failure("invalid params");
			}
			SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
			if (sdvPatient == null) {
				return ResultMessage.failure("invalid param sdvPatientId");
			}
			SdvPatientData sdvPatientData = new SdvPatientData();
			sdvPatientData.setSdvPatientId(sdvPatientId);
			sdvPatientData.setSdvTemplateId(sdvPatient.getSdvTemplateId());
			sdvPatientData.setVisitId(visitId);
			sdvPatientData.setDataId(dataId);
			SdvPatientData data = sdvPatientDataService.selectOneEntry(sdvPatientData);
			if (data != null) {
				SdvPatientRecord sdvPatientRecord = new SdvPatientRecord();
				sdvPatientRecord.setSdvPatientDataId(data.getId());
				List<SdvPatientRecord> datas = sdvPatientRecordService.selectEntryList(sdvPatientRecord);
				int count = sdvPatientRecordService.deleteByCondtion(sdvPatientRecord);
				if (!CollectionUtils.isEmpty(datas)) {
					PatientRecordFile condtion = new PatientRecordFile();
					for (SdvPatientRecord r : datas) {
						condtion.setPatientRecordId(r.getId());
						patientRecordFileService.deleteByCondtion(condtion);
					}
				}
				result.setData(count);
			}
		} catch (Exception e) {
			LOG.error("updateStatus error[{}]", e.getMessage(), e);
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
		result.setData(TranslationUtil.translation(sdvPatientRecordService.selectEntry(id)));
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
				TemplateVisitData condtion3 = new TemplateVisitData();
				condtion3.setVisitId(data.getId());
				List<TemplateVisitData> visitDatas = templateVisitDataService.selectEntryList(condtion3);
				if (CollectionUtils.isEmpty(visitDatas)) {
					data.setEndCount(0);
					data.setAllCount(0);
				} else {
					data.setAllCount(visitDatas.size());
					int endCount = 0;
					for (TemplateVisitData visitData : visitDatas) {
						SdvPatientData condtion4 = new SdvPatientData();
						condtion4.setSdvPatientId(sdvPatientId);
						condtion4.setSdvTemplateId(sdvPatient.getSdvTemplateId());
						condtion4.setVisitId(data.getId());
						condtion4.setDataId(visitData.getDataId());
						SdvPatientData sdvPatientData = sdvPatientDataService.selectOneEntry(condtion4);
						if (sdvPatientData != null && sdvPatientData.getStatus() != null && sdvPatientData.getStatus() == 1) {
							endCount++;
						}
					}
					data.setEndCount(endCount);
				}
			}
		}
		result.setData(TranslationUtil.translations(datas));
		return result;
	}

	/**
	 * 通过访视ID查询访视下数据项列表
	 * 
	 * @param visitId
	 * @return
	 */
	@GetMapping("/patient/{sdvPatientId}/{visitId}")
	public ResultMessage<List<SdvPatientData>> getTemplateVisitDataByVisitId(@PathVariable("sdvPatientId") Long sdvPatientId,
			@PathVariable("visitId") Long visitId) {
		SdvPatient sdvPatient = sdvPatientService.selectEntry(sdvPatientId);
		if (sdvPatient == null) {
			return ResultMessage.failure("invalid param sdvPatientId");
		}
		ResultMessage<List<SdvPatientData>> result = ResultMessage.success();
		TemplateVisitData condtion3 = new TemplateVisitData();
		condtion3.setVisitId(visitId);
		List<TemplateVisitData> visitDatas = templateVisitDataService.selectEntryList(condtion3);
		if (CollectionUtils.isEmpty(visitDatas)) {
			result.setData(new ArrayList<SdvPatientData>());
			return result;
		}
		List<SdvPatientData> records = new ArrayList<SdvPatientData>();
		for (TemplateVisitData data : visitDatas) {
			SdvPatientData condtion4 = new SdvPatientData();
			condtion4.setSdvPatientId(sdvPatientId);
			condtion4.setSdvTemplateId(sdvPatient.getSdvTemplateId());
			condtion4.setVisitId(visitId);
			condtion4.setDataId(data.getDataId());
			SdvPatientData sdvPatientData = sdvPatientDataService.selectOneEntry(condtion4);
			if (sdvPatientData == null) {
				sdvPatientData = new SdvPatientData();
				sdvPatientData.setDataId(data.getDataId());
				sdvPatientData.setSdvPatientId(sdvPatientId);
				sdvPatientData.setSdvTemplateId(sdvPatient.getSdvTemplateId());
				sdvPatientData.setVisitId(visitId);
				sdvPatientData.setStatus(0);
			}
			records.add(sdvPatientData);
		}
		result.setData(TranslationUtil.translations(records));
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
		SdvPatientData condtion4 = new SdvPatientData();
		condtion4.setSdvPatientId(sdvPatientId);
		condtion4.setSdvTemplateId(sdvPatient.getSdvTemplateId());
		condtion4.setVisitId(visitId);
		condtion4.setDataId(dataId);
		SdvPatientData sdvPatientData = sdvPatientDataService.selectOneEntry(condtion4);
		if (sdvPatientData == null) {
			result.setData(new ArrayList<SdvPatientRecord>());
			return result;
		}
		SdvPatientRecord condtion = new SdvPatientRecord();
		condtion.setSdvPatientDataId(sdvPatientData.getId());
		List<SdvPatientRecord> datas = sdvPatientRecordService.selectEntryList(condtion);
		if (!CollectionUtils.isEmpty(datas)) {
			for (SdvPatientRecord record : datas) {
				PatientRecordFile condtion1 = new PatientRecordFile();
				condtion1.setPatientRecordId(record.getId());
				List<PatientRecordFile> files = patientRecordFileService.selectEntryList(condtion1);
				record.setFiles(files);
			}
		}
		result.setData(TranslationUtil.translations(datas));
		return result;
	}

	@PostMapping("/export")
	public ResultMessage<Integer> export(@RequestBody ExportDateRecordDto dto) {
		ResultMessage<Integer> result = ResultMessage.success();
		SdvPatientRecord condtion = new SdvPatientRecord();
		if (dto.getStartDate() != null) {
			condtion.getExtendMap().put("startDate", dto.getStartDate());
		}
		if (dto.getEndDate() != null) {
			condtion.getExtendMap().put("endDate", dto.getEndDate());
		}
		if (!CollectionUtils.isEmpty(dto.getPatientIds())) {
			condtion.getExtendMap().put("patientIds", dto.getPatientIds());
		}
		condtion.setOrderField("sdv_patient_id,visit_id,data_id,create_time");
		condtion.setOrderFieldType("ASC");
		List<SdvPatientRecord> datas = sdvPatientRecordService.selectEntryList(condtion);
		if (CollectionUtils.isEmpty(datas)) {
			result = ResultMessage.failure("没有符合条件的数据");
			return result;
		}
		SXSSFWorkbook workbook = this.toExcel(datas);
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String cosPath = "/" + "访视报告" + "_" + uuid + ".xlsx";
		File file = new File(cosPath);
		OutputStream os;
		try {
			os = new FileOutputStream(file);
			workbook.write(os);
			os.flush();
			os.close();
			COSResult cos = cosClientService.uploadLocalFile(cosClientConfig.getBucketName(), cosPath, file.getPath());
			if (!"0".equals(cos.getCode())) {
				LOG.error("upload file error: {}", cos.getMessage());
				return ResultMessage.failure("导出文件失败");
			}
			String url = cosClientConfig.getFileUrl() + cosPath;
			Map<String, String> params = new HashMap<String, String>();
			params.put("url", url);
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			String startDateString = dto.getStartDate() == null ? "" : df.format(dto.getStartDate());
			String endDateString = dto.getEndDate() == null ? "" : df.format(dto.getEndDate());
			params.put("startDateString", startDateString);
			params.put("endDateString", endDateString);
			String patientNames = "";
			if (!CollectionUtils.isEmpty(dto.getPatientIds())) {
				for (Long id : dto.getPatientIds()) {
					SdvPatient sdvPatient = sdvPatientService.selectEntry(id);
					if (sdvPatient != null) {
						patientNames += sdvPatient.getPatientNo();
					}
				}
			}
			params.put("patientNames", patientNames);
			params.put("weixinAccount", this.weixinAccount);
			this.sendMail(dto.getEmail(), "收到来自监查笔记的报告", "email.ftl", params);
			// 发送邮件
			result.setData(datas.size());
		} catch (Exception e) {
			LOG.error("export error[{}]", e.getMessage(), e);
			result = ResultMessage.failure("导出异常");
		} finally {
			file.delete();
		}
		return result;
	}

	private SXSSFWorkbook toExcel(List<SdvPatientRecord> datas) {
		SXSSFWorkbook workbook = new SXSSFWorkbook(100);
		Sheet sheet = workbook.createSheet("sheet1");
		Row title = sheet.createRow(0);
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 16);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleStyle.setFont(font);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		Cell cell = title.createCell(0);
		cell.setCellValue("访视报告");
		cell.setCellStyle(titleStyle);

		Row header = sheet.createRow(1);
		this.createCell(workbook, header, 0, "时间");
		this.createCell(workbook, header, 1, "SDV表名称");
		this.createCell(workbook, header, 2, "患者编号");
		this.createCell(workbook, header, 3, "患者姓名缩写");
		this.createCell(workbook, header, 4, "所属访视期");
		this.createCell(workbook, header, 5, "核查项");
		this.createCell(workbook, header, 6, "笔记内容");
		sheet.setColumnWidth(0, 20 * 256);
		sheet.setColumnWidth(1, 30 * 256);
		sheet.setColumnWidth(2, 20 * 256);
		sheet.setColumnWidth(3, 20 * 256);
		sheet.setColumnWidth(4, 20 * 256);
		sheet.setColumnWidth(5, 30 * 256);
		sheet.setColumnWidth(6, 80 * 256);
		int index = 2;
		Iterator<SdvPatientRecord> it = datas.iterator();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Map<Long, String> templateMap = new HashMap<Long, String>();
		Map<Long, SdvPatient> patientMap = new HashMap<Long, SdvPatient>();
		Map<Long, String> visitMap = new HashMap<Long, String>();
		Map<Long, String> dataMap = new HashMap<Long, String>();
		while (it.hasNext()) {
			SdvPatientRecord t = it.next();
			Row row = sheet.createRow(index);
			this.createCell(workbook, row, 0, df.format(t.getCreateTime()));
			String templateName = templateMap.get(t.getSdvTemplateId());
			if (templateName == null) {
				SdvTemplate template = sdvTemplateService.selectEntry(t.getSdvTemplateId());
				if (template != null) {
					templateName = template.getName();
				} else {
					templateName = "";
				}
				templateMap.put(t.getSdvTemplateId(), templateName);
			}
			this.createCell(workbook, row, 1, templateName);
			SdvPatient patient = patientMap.get(t.getSdvPatientId());
			if (patient == null) {
				patient = sdvPatientService.selectEntry(t.getSdvPatientId());
				patientMap.put(t.getSdvPatientId(), patient);
			}
			this.createCell(workbook, row, 2, patient == null ? "" : patient.getPatientNo());
			this.createCell(workbook, row, 3, patient == null ? "" : patient.getPatientName());
			String visitName = visitMap.get(t.getVisitId());
			if (visitName == null) {
				SdvTemplateVisit visit = sdvTemplateVisitService.selectEntry(t.getVisitId());
				if (visit != null) {
					visitName = visit.getName();
				} else {
					visitName = "";
				}
				visitMap.put(t.getVisitId(), visitName);
			}
			this.createCell(workbook, row, 4, visitName);

			String dataName = dataMap.get(t.getDataId());
			if (dataName == null) {
				SdvTemplateData data = sdvTemplateDataService.selectEntry(t.getDataId());
				if (data != null) {
					dataName = data.getName();
				} else {
					dataName = "";
				}
				dataMap.put(t.getVisitId(), dataName);
			}
			this.createCell(workbook, row, 5, dataName);
			this.createCell(workbook, row, 6, t.getRemark());
			index++;
		}
		return workbook;
	}

	private void createCell(SXSSFWorkbook workbook, Row row, int cellNum, String value) {
		if (row != null) {
			Cell cell = row.createCell(cellNum);
			cell.setCellValue(new HSSFRichTextString(value == null ? "" : value.toString()));
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			DataFormat format = workbook.createDataFormat();
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setDataFormat(format.getFormat("@"));
			cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle2.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			cellStyle2.setFont(font);
			cell.setCellStyle(cellStyle2);
		}
	}

	private void sendMail(String receive, String subject, String templateName, Map<String, String> params) {
		Configuration cfg = new Configuration();
		try {
			cfg.setClassForTemplateLoading(this.getClass(), "/templates");// 类路径
			Template template = cfg.getTemplate(templateName);
			template.setEncoding("UTF-8");
			StringWriter writer = new StringWriter();
			template.process(params, writer);// 页面参数与模版文件交互
			String content = writer.toString();
			emailService.sendEmail("userform@mail.ourred.cn", receive, subject, content);
		} catch (Exception e) {
			LOG.error("发送邮件失败：{}", e.getMessage(), e);
		}
	}
}