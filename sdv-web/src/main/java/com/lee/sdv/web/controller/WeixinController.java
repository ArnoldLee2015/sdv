package com.lee.sdv.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.web.controller.domain.ResultMessage;

/**
 * Created by lipeng
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinController.class);

	@Resource
	private WeiXinService weiXinService;
	@Resource
	private SdvUserService sdvUserService;

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
		String sessionKey = weiXinService.jscode2session(code);
		if (Strings.isNullOrEmpty(sessionKey)) {
			return ResultMessage.failure("登录失败");
		}
		result.setData(sessionKey);
		return result;
	}

	/**
	 * web用户登录
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	@GetMapping("/webLogin/{key}")
	public ResultMessage<Boolean> webLogin(@PathVariable("key") String key,
			HttpServletRequest request) {
		if (Strings.isNullOrEmpty(key)) {
			return ResultMessage.failure("key is null");
		}
		LOG.error("localSessionKey-----{}", key);
		ResultMessage<Boolean> result = ResultMessage.success();
		SdvUser sdvUser = sdvUserService.selectByLocalSessionKey(key);
		if (sdvUser == null) {
			return ResultMessage.failure("登录失败");
		}
		request.getSession().setAttribute("sdvUser", sdvUser);
		result.setData(true);
		return result;
	}

	/**
	 * web用户登出
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/webLoginOut")
	public ResultMessage<Boolean> webLoginOut(HttpServletRequest request) {
		request.getSession().removeAttribute("sdvUser");
		ResultMessage<Boolean> result = ResultMessage.success();
		result.setData(true);
		return result;
	}
}
