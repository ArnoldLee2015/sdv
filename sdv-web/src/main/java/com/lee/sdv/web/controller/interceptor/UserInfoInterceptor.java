package com.lee.sdv.web.controller.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.base.Strings;
import com.lee.sdv.domain.SdvUser;
import com.lee.sdv.service.SdvUserService;

public class UserInfoInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger(UserInfoInterceptor.class);
	@Resource
	private SdvUserService sdvUserService;

	/**
	 * 权限拦截处理
	 */
	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object handler)
			throws Exception {
		try {
			SdvUser sdvUser = null;
			// 小程序验证用户是否登录
			String localSessionKey = httpServletRequest.getHeader("ticket");
//			localSessionKey="cb43bf53d2614983b78ca074868fd8f1";
			if (Strings.isNullOrEmpty(localSessionKey)) {
				// 判断是否web登录
				sdvUser = (SdvUser) httpServletRequest.getSession()
						.getAttribute("sdvUser");
				if (sdvUser == null) {
					this.sendErrorMessage(httpServletRequest,
							httpServletResponse, "-1");
					return false;
				}
			} else {
				sdvUser = sdvUserService
						.selectByLocalSessionKey(localSessionKey);
				if (sdvUser == null
						|| sdvUser.getExpireDate().before(new Date())) {
					this.sendErrorMessage(httpServletRequest,
							httpServletResponse, "-1");
					return false;
				}
			}
			// 设置请求用户上下文信息
			this.setUserContext(sdvUser);
			return true;
		} catch (Exception e) {
			LOG.error("preHandle error", e);
		}
		return false;
	}

	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object handler, Exception e)
			throws Exception {
		// 清理线程变量
		UserContext.remove();
	}

	protected void sendErrorMessage(HttpServletRequest request,
			HttpServletResponse response, String code) throws IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json;charset=UTF-8");
		out.print(new StringBuilder()
				.append("{\"status\":false,\"msg\":\"NoLoginWarning\",\"code\":\"")
				.append(code).append("\"}").toString());
		out.flush();
		out.close();
	}

	protected UserContext setUserContext(SdvUser sdvUser) {
		UserContext userContext = new UserContext();
		userContext.setId(sdvUser.getId());
		userContext.setLocalSessionKey(sdvUser.getLocalSessionKey());
		userContext.setName(sdvUser.getName());
		UserContext.setUserContext(userContext);
		return userContext;
	}

}
