/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Sets;
import com.lee.sdv.domain.PatientRecordFile;
import com.lee.sdv.service.cos.COSClientService;
import com.lee.sdv.service.cos.domain.COSClientConfig;
import com.lee.sdv.service.cos.domain.COSResult;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.util.FileUtil;

/**
 * 文件上传接口
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/upload")
public class FileUploadController {
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	private final static Set<String> DEFAULT_ALLOWED_IMAGE_TYPES = Sets.newHashSet("jpg", "bmp", "png");// image/jpg,image/jpeg,image/png,image/bmp
	@Autowired
	private COSClientService cosClientService;
	@Autowired
	private COSClientConfig cosClientConfig;

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param req
	 * @param resp
	 * @param context
	 * @return
	 */
	@PostMapping("")
	public ResultMessage<PatientRecordFile> upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest req,
			HttpServletResponse resp, ModelMap context) {
		ResultMessage<PatientRecordFile> result = ResultMessage.success();
		// 上传图片
		byte[] fileBytes = file.getBytes();
		String fileType = FileUtil.getFileTypeByStream(fileBytes);
		if (StringUtils.isBlank(fileType) || !DEFAULT_ALLOWED_IMAGE_TYPES.contains(fileType)) {// 限定文件类型
			return ResultMessage.failure("非法的文件类型.");
		}
		String fileName = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String cosPath = "/" + uuid + "_" + fileName;
		COSResult cos = cosClientService.uploadFile(cosClientConfig.getBucketName(), cosPath, fileBytes);
		if (!"0".equals(cos.getCode())) {
			LOG.error("upload file error: {}", cos.getMessage());
			return ResultMessage.failure("上传文件失败");
		}
		PatientRecordFile patientRecordFile = new PatientRecordFile();
		patientRecordFile.setFileName(fileName);
		patientRecordFile.setFileType(fileType);
		patientRecordFile.setFileUrl(cosClientConfig.getFileUrl() + cosPath);
		result.setData(patientRecordFile);
		return result;
	}

	/**
	 * 获取签名
	 * 
	 * @param cosPath
	 *            文件存储路径
	 * @param expireSecond
	 *            过期时间，单位秒
	 * @return
	 */
	@GetMapping("/sign")
	public ResultMessage<String> sign(String cosPath, Integer expireSecond) {
		ResultMessage<String> result = ResultMessage.success();
		Calendar c = Calendar.getInstance();
		if (expireSecond == null) {
			expireSecond = 60;
		}
		c.add(Calendar.SECOND, expireSecond);
		String sign = cosClientService.sign(cosPath, c.getTime());
		result.setData(sign);
		return result;
	}
}