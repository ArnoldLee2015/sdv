/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.web.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * 系统用户服务
 * 
 * @author lipeng
 */
@RestController
@RequestMapping(value = "/api/sdvUser")
public class SdvUserController {
	private static final Logger LOG = LoggerFactory.getLogger(SdvUserController.class);
	@Autowired
	private SdvUserService sdvUserService;

	/**
	 * 新增/修改信息
	 * 
	 * @param t
	 * @return
	 */
	@PutMapping("/save")
	public ResultMessage<Long> saveSdvUser(@RequestBody SdvUser t) {
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
			sdvUserService.saveOrUpdate(t);
			result.setData(t.getId());
		} catch (Exception e) {
			LOG.error("saveSdvUser error[{}]", e.getMessage(), e);
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
	public ResultMessage<Integer> deleteSdvUser(@PathVariable("id") Long id) {
		ResultMessage<Integer> result = ResultMessage.success();
		try {
			result.setData(sdvUserService.deleteByKey(id));
		} catch (Exception e) {
			LOG.error("deleteSdvUser error[{}]", e.getMessage(), e);
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
	public ResultMessage<SdvUser> getSdvUser(@PathVariable("id") Long id) {
		ResultMessage<SdvUser> result = ResultMessage.success();
		result.setData(sdvUserService.selectEntry(id));
		return result;
	}

	/**
	 * 通过当前登录用户信息
	 * 
	 * @return
	 */
	@GetMapping("")
	public ResultMessage<SdvUser> getCurrentSdvUser() {
		ResultMessage<SdvUser> result = ResultMessage.success();
		UserContext user = UserContext.getUserContext();
		result.setData(sdvUserService.selectEntry(user.getId()));
		return result;
	}

}