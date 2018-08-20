/**
 * 
 */
package com.hp.core.webjars.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hp.core.common.beans.EnumTypeRequestBean;
import com.hp.core.common.beans.Response;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.ObjectUtil;
import com.hp.core.webjars.constants.BaseConstant;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;


/**
 * 描述：
 * 
 * @author ping.huang 2016年5月6日
 */
@RestController
@RequestMapping("/NoFilterController")
public class NoFilterController {

	static Logger log = LoggerFactory.getLogger(NoFilterController.class);
	

	/**
	 * 获取枚举的列表
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getEnumForSelect")
	public Response<JSONArray> getEnumForSelect(EnumTypeRequestBean bo) throws Exception {
		log.info("enter getEnumForSelect with EnumTypeRequestBO={}", bo);
		String className = bo.getClassName();
		if (className.indexOf(".") < 0) {
			className = "com.hp.core.webjars.enums." + className;
		}
		JSONArray arr = (JSONArray) ObjectUtil.executeJavaMethod(Class.forName(className), "toJSON", null, null);
		if (bo.getFirstText() != null && bo.getFirstValue() != null) {
			JSONObject json = new JSONObject();
			json.put("text", bo.getFirstText());
			json.put("value", bo.getFirstValue());
			arr.add(0, json);
		}
		return new Response<JSONArray>(arr);
	}
	
	/**
	 * 获取登录的用户信息
	 * 
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping(params = "method=getUserInfo")
	@ResponseBody
	public Response<SysUserResponseBO> getUserInfo(HttpSession session) throws CommonException {
		SysUserResponseBO bo = (SysUserResponseBO) session.getAttribute(BaseConstant.USER_SESSION);
		return new Response<>(bo);
	}
	
	/**
	 * 查询用户的权限菜单（从session中获取）
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=queryUserMenuFromSession")
	@ResponseBody
	public Response<List<SysMenuResponseBO>> queryUserMenuFromSession(HttpSession session) {
		log.info("queryUserSessionMenu start");
		List<SysMenuResponseBO> list = (List<SysMenuResponseBO>) session.getAttribute(BaseConstant.USER_MENU);
		log.info("queryUserSessionMenu success");
		return new Response<>(list);
	}
	
	/**
	 * 根据菜单id，查询该菜单下的按钮
	 * @param session
	 * @param menuId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=querySessionButtonByMenuId")
	@ResponseBody
	public Response<List<SysMenuResponseBO>> querySessionButtonByMenuId(HttpSession session, Integer menuId) {
		log.info("querySessionButtonByMenuId start");
		List<SysMenuResponseBO> list = (List<SysMenuResponseBO>) session.getAttribute(BaseConstant.USER_MENU);
		List<SysMenuResponseBO> respList = new ArrayList<>();
		for (SysMenuResponseBO bo : list) {
			if (bo.getParentMenuId().intValue() == menuId.intValue()) {
				respList.add(bo);
			}
		}
		log.info("querySessionButtonByMenuId success");
		return new Response<>(respList);
	}

}
