package com.lee.sdv.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.web.controller.domain.ResultMessage;

/**
 * Created by lipeng
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {
	private static final Logger LOG = LoggerFactory.getLogger(WeixinController.class);

	@Resource
	private WeiXinService weiXinService;

	/**
	 * 获取用户鉴权信息
	 *
	 * @param url
	 * @param request
	 * @param response
	 */
	@GetMapping("/login/{code}")
	public ResultMessage<String> login(@PathVariable("code") String code) {
		if (Strings.isNullOrEmpty(code)) {
			return ResultMessage.failure("code is null");
		}
		LOG.error("jscode-----{}", code);
		ResultMessage<String> result = ResultMessage.success();
		result.setData(weiXinService.jscode2session(code));
		return result;
	}

}
