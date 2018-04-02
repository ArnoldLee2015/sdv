package com.lee.sdv.web.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.dto.LoginDto;

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
	@PostMapping("/login")
	public ResultMessage<String> login(@RequestBody LoginDto loginDto) {
		if (Strings.isNullOrEmpty(loginDto.getCode())) {
			return ResultMessage.failure("code is null");
		}
		String code = loginDto.getCode();
		LOG.error("jscode-----{}", code);
		ResultMessage<String> result = ResultMessage.success();
		SdvUser sdvUser = weiXinService.jscode2session(code,
				loginDto.getEncryptedData(), loginDto.getIv());
		if (sdvUser == null) {
			return ResultMessage.failure("登录失败");
		}
		if (Strings.isNullOrEmpty(sdvUser.getLocalSessionKey())) {
			return ResultMessage.failure("登录失败");
		}
		result.setData(sdvUser.getLocalSessionKey());
		return result;
	}

	/**
	 * web用户登录
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	/*
	 * @GetMapping("/webLogin") public ResultMessage<Boolean> webLogin(String
	 * code, HttpServletRequest request) { if (Strings.isNullOrEmpty(code)) {
	 * return ResultMessage.failure("key is null"); }
	 * LOG.error("localSessionKey-----{}", code); ResultMessage<Boolean> result
	 * = ResultMessage.success(); SdvUser sdvUser =
	 * weiXinService.accessToken(code); if (sdvUser == null) { return
	 * ResultMessage.failure("登录失败"); }
	 * request.getSession().setAttribute("sdvUser", sdvUser);
	 * result.setData(true); return result; }
	 * 
	 * @GetMapping("/webLogin/refresh") public ResultMessage<Boolean>
	 * webLoginRefresh(HttpServletRequest request) { ResultMessage<Boolean>
	 * result = ResultMessage.success(); UserContext user =
	 * UserContext.getUserContext(); if (user == null) { return
	 * ResultMessage.failure("未登录"); } SdvUser sdvUser =
	 * sdvUserService.selectEntry(user.getId()); if (sdvUser.getRefreshToken()
	 * == null) { return ResultMessage.failure("web登录异常"); }
	 * weiXinService.refreshToken(sdvUser); result.setData(true); return result;
	 * }
	 *//**
	 * web用户登出
	 * 
	 * @param request
	 * @return
	 */
	/*
	 * @GetMapping("/webLoginOut") public ResultMessage<Boolean>
	 * webLoginOut(HttpServletRequest request) {
	 * request.getSession().removeAttribute("sdvUser"); ResultMessage<Boolean>
	 * result = ResultMessage.success(); result.setData(true); return result; }
	 */
}
