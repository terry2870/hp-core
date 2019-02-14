/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.mybatis.autocreate.helper.AutoCreateBean;

/**
 * @author huangping
 * 2018年9月19日
 */
@RestController
@RequestMapping("/AutoCreateRest")
public class AutoCreateRest {
	
	/**
	 * 自动生成
	 * @param bean
	 * @return
	 */
	@RequestMapping("/create")
	public Response<?> create(@RequestBody AutoCreateBean bean, HttpServletRequest request) {
		CreateFile.main(bean, request.getContextPath());
		return new Response<>();
	}
}
