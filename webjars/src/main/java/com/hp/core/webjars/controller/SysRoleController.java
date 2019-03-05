package com.hp.core.webjars.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.core.common.beans.Response;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.database.bean.PageRequest;
import com.hp.core.webjars.model.request.SysRoleRequestBO;
import com.hp.core.webjars.model.response.SysRoleResponseBO;
import com.hp.core.webjars.service.ISysRoleMenuService;
import com.hp.core.webjars.service.ISysRoleService;

/**
 * 系统角色表控制器
 * @author huangping
 * 2018-08-06
 */
@Controller
@RequestMapping("/SysRoleController")
public class SysRoleController {

	static Logger log = LoggerFactory.getLogger(SysRoleController.class);

	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;

	@RequestMapping("/sysRoleList")
	public String sysRoleList(HttpServletRequest request) {
		request.setAttribute("menuId", request.getParameter("menuId"));
		request.setAttribute("iframeId", request.getParameter("iframeId"));
		return "sysManage/sysRole/sysRoleList";
	}
	
	/**
	 * 查询系统角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/queryAllSysRole")
	@ResponseBody
	public Response<PageResponse<SysRoleResponseBO>> queryAllSysRole(SysRoleRequestBO request, PageRequest pageRequest) {
		log.info("queryAllSysRole with request={}, page={}", request, pageRequest);
		PageResponse<SysRoleResponseBO> list = sysRoleService.querySysRolePageList(request, pageRequest);
		log.info("queryAllSysRole success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return new Response<>(list);
	}

	/**
	 * 保存系统角色表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysRole")
	@ResponseBody
	public Response<Object> saveSysRole(SysRoleRequestBO request) {
		log.info("saveSysRole with request={}", request);
		sysRoleService.saveSysRole(request);
		log.info("saveSysRole success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除系统角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysRole")
	@ResponseBody
	public Response<Object> deleteSysRole(Integer id) {
		log.info("deleteSysRole with id={}", id);
		sysRoleService.deleteSysRole(id);
		log.info("deleteSysRole success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询系统角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysRoleById")
	@ResponseBody
	public Response<SysRoleResponseBO> querySysRoleById(Integer id) {
		log.info("querySysRoleById with id={}", id);
		SysRoleResponseBO bo = sysRoleService.querySysRoleById(id);
		log.info("querySysRoleById success. with id={}", id);
		return new Response<>(bo);
	}
	
	/**
	 * 查询该角色的菜单
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/selectMenuByRoleId")
	@ResponseBody
	public Response<List<Integer>> selectMenuByRoleId(Integer roleId) {
		log.info("selectMenuByRoleId with roleId={}", roleId);
		List<Integer> list = sysRoleMenuService.selectMenuByRoleId(roleId);
		return new Response<>(list);
	}
	
	/**
	 * 保存角色菜单
	 * @param roleId
	 * @param menuIds
	 */
	@RequestMapping("/saveSysRoleMenu")
	@ResponseBody
	public Response<Object> saveSysRoleMenu(Integer roleId, String menuIds) {
		log.info("saveSysRoleMenu with roleId={}, menuIds={}", roleId, menuIds);
		sysRoleMenuService.saveSysRoleMenu(roleId, menuIds);
		return new Response<>();
	}
}
