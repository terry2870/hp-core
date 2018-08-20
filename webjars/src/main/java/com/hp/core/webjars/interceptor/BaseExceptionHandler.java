/**
 * 
 */
package com.hp.core.webjars.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.webjars.enums.CodeEnum;
import com.hp.core.webjars.exceptions.NoRightException;

/**
 * @author huangping 2016年8月24日 上午1:02:17
 */
@Component
public class BaseExceptionHandler implements HandlerExceptionResolver {

	static Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object paramObject, Exception exception) {
		log.warn("enter resolveException with exception={}", exception.getMessage());
		// 数据库超时异常，特殊处理
		if (exception instanceof QueryTimeoutException) {
			response.setStatus(CodeEnum.DATABASE_TIME_OUT.getCode());
			return getErrorJsonView(CodeEnum.DATABASE_TIME_OUT.getCode(), CodeEnum.DATABASE_TIME_OUT.getMessage());
		}
		if (exception instanceof NoRightException) {
			NoRightException e = (NoRightException) exception;
			response.setStatus(e.getCode());
			return getErrorJsonView(e.getCode(), e.getMessage());
		}
		if (exception instanceof CommonException) {
			CommonException commonException = (CommonException) exception;
			response.setStatus(commonException.getCode());
			return getErrorJsonView(commonException.getCode(), commonException.getMessage());
		}
		log.error("enter resolveException with", exception);
		response.setStatus(CodeEnum.ERROR.getCode());
		return getErrorJsonView(CodeEnum.ERROR.getCode(), exception.getMessage());
	}

	/**
	 * 使用FastJson提供的FastJsonJsonView视图返回，不需要捕获异常
	 */
	private ModelAndView getErrorJsonView(int code, String message) {
		ModelAndView modelAndView = new ModelAndView();
		FastJsonJsonView jsonView = new FastJsonJsonView();
		Map<String, Object> errorInfoMap = new HashMap<>();
		errorInfoMap.put("code", code);
		errorInfoMap.put("message", message);
		jsonView.setAttributesMap(errorInfoMap);
		modelAndView.setView(jsonView);
		return modelAndView;
	}
}
