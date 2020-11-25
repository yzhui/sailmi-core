package com.sailmi.core.oss.common.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 扩展 <code>org.springframework.web.util.WebUtils</code>
 *
 * @since 1.0
 */
public abstract class WebUtils extends org.springframework.web.util.WebUtils {


	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * spring版本较低，不支持该方法
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}


	public static HttpSession getSession() {
		return getRequest().getSession();
	}


	public static ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}


	public static String getContextPath() {
		return getRequest().getContextPath();
	}


	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie cookie = getCookie(request, key);
		if(cookie != null) {
			return cookie.getValue();
		}
		return "";
	}

	public static String getSessionIdWithCookie(HttpServletRequest request, String cookieKey) {
		String sessionId = request.getSession().getId();
		Cookie cookie = getCookie(request, cookieKey);
		if(cookie != null) {
			sessionId = cookie.getValue();
		}
		return sessionId;
	}

	/**
	 * 获取当前请求的ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("X-Real-IP");
		if(!StringUtils.isBlank(ip)&&!"unknown".equalsIgnoreCase(ip))
		{
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if(!StringUtils.isBlank(ip)&& !"unknown".equalsIgnoreCase(ip))
		{
			int index = ip.indexOf(',');
			if(index != -1){
				return ip.substring(0, index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("Proxy-Client-IP");
		if(!StringUtils.isBlank(ip)&&!"unknown".equalsIgnoreCase(ip))
		{
			return ip;
		}
		ip = request.getHeader("WL-Proxy-Client-IP");
		if(!StringUtils.isBlank(ip)&&!"unknown".equalsIgnoreCase(ip))
		{
			return ip;
		}
		ip = request.getRemoteAddr();
		if("0:0:0:0:0:0:0:1".equals(ip)){
			ip = "127.0.0.1";
		}
		return ip;
	}
}

