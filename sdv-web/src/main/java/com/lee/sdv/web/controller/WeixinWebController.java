package com.lee.sdv.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;
import com.lee.sdv.service.weixin.WeiXinService;
import com.lee.sdv.service.weixin.domain.ApiConfig;
import com.lee.sdv.web.controller.domain.ResultMessage;
import com.lee.sdv.web.controller.interceptor.UserContext;

/**
 * Created by lipeng
 */
@Controller
@RequestMapping("/weixinWeb")
public class WeixinWebController {
	private static final Logger LOG = LoggerFactory
			.getLogger(WeixinWebController.class);

	@Resource
	private WeiXinService weiXinService;
	@Resource
	private SdvUserService sdvUserService;
	@Autowired
	private ApiConfig apiConfig;
	@Value("${web.login.url}")
	private String loginUrl;

	/**
	 * web用户登录
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	@GetMapping("/webLogin")
	public String webLogin(String code, HttpServletRequest request) {
		if (Strings.isNullOrEmpty(code)) {
			try {
				return "redirect:"
						+ String.format(ApiConfig.loginCode,
								apiConfig.getAppOpenId(),
								URLEncoder.encode(loginUrl, "utf-8"), "web");
			} catch (UnsupportedEncodingException e) {
				LOG.error("redirect error ", e);
			}
		}
		LOG.error("localSessionKey-----{}", code);
		SdvUser sdvUser = weiXinService.accessToken(code);
		if (sdvUser == null) {
			try {
				return "redirect:"
						+ String.format(ApiConfig.loginCode,
								apiConfig.getAppOpenId(),
								URLEncoder.encode(loginUrl, "utf-8"), "web");
			} catch (UnsupportedEncodingException e) {
				LOG.error("redirect error ", e);
			}
		}
		request.getSession().setAttribute("sdvUser", sdvUser);
		// 重定向到 首页
		return "redirect:/#/";
	}

	@GetMapping("/webLogin/refresh")
	@ResponseBody
	public ResultMessage<Boolean> webLoginRefresh(HttpServletRequest request) {
		ResultMessage<Boolean> result = ResultMessage.success();
		UserContext user = UserContext.getUserContext();
		if (user == null) {
			return ResultMessage.failure("未登录");
		}
		SdvUser sdvUser = sdvUserService.selectEntry(user.getId());
		if (sdvUser.getRefreshToken() == null) {
			return ResultMessage.failure("web登录异常");
		}
		weiXinService.refreshToken(sdvUser);
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
	public String webLoginOut(HttpServletRequest request) {
		request.getSession().removeAttribute("sdvUser");
		try {
			return "redirect:"
					+ String.format(ApiConfig.loginCode,
							apiConfig.getAppOpenId(),
							URLEncoder.encode(loginUrl, "utf-8"), "web");
		} catch (UnsupportedEncodingException e) {
			LOG.error("redirect error ", e);
		}
		return null;
	}
}
