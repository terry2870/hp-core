package com.hp.core.webjars.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.core.common.beans.Response;
import com.hp.core.webjars.model.request.SysMenuRequestBO;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.service.ISysMenuService;

/**
 * 系统菜单表控制器
 * @author huangping
 * 2018-08-06
 */
@Controller
@RequestMapping("/SysMenuController")
public class SysMenuController {

	static Logger log = LoggerFactory.getLogger(SysMenuController.class);

	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 菜单首页
	 * @return
	 */
	@RequestMapping("/sysMenuList")
	public String sysMenuList() {
		return "sysManage/sysMenu/sysMenuList";
	}
	
	/**
	 * 查询当前登录用户所能看到的菜单
	 * @throws Exception
	 */
	@RequestMapping("/queryAllSysMenu")
	@ResponseBody
	public List<SysMenuResponseBO> queryAllSysMenu() throws Exception {
		log.info("queryAllSysMenu start");
		List<SysMenuResponseBO> list = sysMenuService.queryAllSysMenu();
		if (list == null) {
			return new ArrayList<>();
		}
		log.info("queryAllSysMenu success");
		return list;
	}

	/**
	 * 保存系统菜单表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysMenu")
	@ResponseBody
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
	@ResponseBody
	public Response<Object> deleteSysMenu(Integer id) {
		log.info("deleteSysMenu with id={}", id);
		sysMenuService.deleteSysMenu(id);
		log.info("deleteSysMenu success. with id={}", id);
		return new Response<>();
	}
}
