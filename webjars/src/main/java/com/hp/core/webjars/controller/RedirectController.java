/**
 * 
 */
package com.hp.core.webjars.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 公共的跳转控制器
 * 
 * @author huangping 2018年8月3日
 */
@Controller
@RequestMapping("/RedirectController")
public class RedirectController {

	private Logger log = LoggerFactory.getLogger(RedirectController.class);

	/**
	 * 公共的跳转方法
	 * 
	 * @param request
	 * @param redirectUrl
	 * @return
	 */
	@RequestMapping("/forward")
	public String forward(HttpServletRequest request, String redirectUrl) {
		log.info("forward to redirectUrl={}", redirectUrl);
		Map<String, String[]> parameters = request.getParameterMap();
		if (MapUtils.isNotEmpty(parameters)) {
			for (Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getValue() != null) {
					request.setAttribute(entry.getKey(), entry.getValue()[0]);
				}
			}
		}
		return "freemarker/" + redirectUrl;
	}

}
