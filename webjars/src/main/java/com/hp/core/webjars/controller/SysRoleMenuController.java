package com.hp.core.webjars.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.webjars.model.request.SysRoleMenuRequestBO;
import com.hp.core.webjars.model.response.SysRoleMenuResponseBO;
import com.hp.core.webjars.service.ISysRoleMenuService;

/**
 * 系统角色菜单关联表控制器
 * @author huangping
 * 2018-08-06
 */
@RestController
@RequestMapping("/SysRoleMenuController")
public class SysRoleMenuController {

	static Logger log = LoggerFactory.getLogger(SysRoleMenuController.class);

	@Autowired
	private ISysRoleMenuService sysRoleMenuService;

	/**
	 * 查询系统角色菜单关联表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/queryAllSysRoleMenu.do")
	public Response<PageResponse<SysRoleMenuResponseBO>> queryAllSysRoleMenu(SysRoleMenuRequestBO request, PageRequest pageRequest) {
		log.info("queryAllSysRoleMenu with request={}, page={}", request, pageRequest);
		PageResponse<SysRoleMenuResponseBO> list = sysRoleMenuService.querySysRoleMenuPageList(request, pageRequest);
		log.info("queryAllSysRoleMenu success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return new Response<>(list);
	}

	/**
	 * 保存系统角色菜单关联表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysRoleMenu.do")
	public Response<Object> saveSysRoleMenu(SysRoleMenuRequestBO request) {
		log.info("saveSysRoleMenu with request={}", request);
		sysRoleMenuService.saveSysRoleMenu(request);
		log.info("saveSysRoleMenu success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除系统角色菜单关联表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysRoleMenu.do")
	public Response<Object> deleteSysRoleMenu(Integer id) {
		log.info("deleteSysRoleMenu with id={}", id);
		sysRoleMenuService.deleteSysRoleMenu(id);
		log.info("deleteSysRoleMenu success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询系统角色菜单关联表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysRoleMenuById.do")
	public Response<SysRoleMenuResponseBO> querySysRoleMenuById(Integer id) {
		log.info("querySysRoleMenuById with id={}", id);
		SysRoleMenuResponseBO bo = sysRoleMenuService.querySysRoleMenuById(id);
		log.info("querySysRoleMenuById success. with id={}", id);
		return new Response<>(bo);
	}
}
