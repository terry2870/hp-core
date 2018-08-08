package com.hp.core.webjars.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.webjars.constants.BaseConstant;
import com.hp.core.webjars.model.request.SysMenuRequestBO;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.service.ISysMenuService;

/**
 * 系统菜单表控制器
 * @author huangping
 * 2018-08-06
 */
@RestController
@RequestMapping("/SysMenuController")
public class SysMenuController {

	static Logger log = LoggerFactory.getLogger(SysMenuController.class);

	@Autowired
	private ISysMenuService sysMenuService;

	
	/**
	 * 查询用户的权限菜单（从session中获取）
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryUserMenuFromSession")
	@ResponseBody
	public Response<List<SysMenuResponseBO>> queryUserMenuFromSession(HttpSession session) {
		log.info("queryUserSessionMenu start");
		List<SysMenuResponseBO> list = (List<SysMenuResponseBO>) session.getAttribute(BaseConstant.USER_MENU);
		log.info("queryUserSessionMenu success");
		return new Response<>(list);
	}

	/**
	 * 保存系统菜单表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysMenu")
	public Response<Object> saveSysMenu(SysMenuRequestBO request) {
		log.info("saveSysMenu with request={}", request);
		sysMenuService.saveSysMenu(request);
		log.info("saveSysMenu success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除系统菜单表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysMenu")
	public Response<Object> deleteSysMenu(Integer id) {
		log.info("deleteSysMenu with id={}", id);
		sysMenuService.deleteSysMenu(id);
		log.info("deleteSysMenu success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询系统菜单表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysMenuById")
	public Response<SysMenuResponseBO> querySysMenuById(Integer id) {
		log.info("querySysMenuById with id={}", id);
		SysMenuResponseBO bo = sysMenuService.querySysMenuById(id);
		log.info("querySysMenuById success. with id={}", id);
		return new Response<>(bo);
	}
}
