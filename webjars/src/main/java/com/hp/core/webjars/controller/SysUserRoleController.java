package com.hp.core.webjars.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.webjars.model.request.SysUserRoleRequestBO;
import com.hp.core.webjars.model.response.SysUserRoleResponseBO;
import com.hp.core.webjars.service.ISysUserRoleService;

/**
 * 用户角色表控制器
 * @author huangping
 * 2018-08-06
 */
@RestController
@RequestMapping("/SysUserRoleController")
public class SysUserRoleController {

	static Logger log = LoggerFactory.getLogger(SysUserRoleController.class);

	@Autowired
	private ISysUserRoleService sysUserRoleService;

	/**
	 * 查询用户角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/queryAllSysUserRole.do")
	public Response<PageResponse<SysUserRoleResponseBO>> queryAllSysUserRole(SysUserRoleRequestBO request, PageRequest pageRequest) {
		log.info("queryAllSysUserRole with request={}, page={}", request, pageRequest);
		PageResponse<SysUserRoleResponseBO> list = sysUserRoleService.querySysUserRolePageList(request, pageRequest);
		log.info("queryAllSysUserRole success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return new Response<>(list);
	}

	/**
	 * 保存用户角色表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysUserRole.do")
	public Response<Object> saveSysUserRole(SysUserRoleRequestBO request) {
		log.info("saveSysUserRole with request={}", request);
		sysUserRoleService.saveSysUserRole(request);
		log.info("saveSysUserRole success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除用户角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysUserRole.do")
	public Response<Object> deleteSysUserRole(Integer id) {
		log.info("deleteSysUserRole with id={}", id);
		sysUserRoleService.deleteSysUserRole(id);
		log.info("deleteSysUserRole success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询用户角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysUserRoleById.do")
	public Response<SysUserRoleResponseBO> querySysUserRoleById(Integer id) {
		log.info("querySysUserRoleById with id={}", id);
		SysUserRoleResponseBO bo = sysUserRoleService.querySysUserRoleById(id);
		log.info("querySysUserRoleById success. with id={}", id);
		return new Response<>(bo);
	}
}
