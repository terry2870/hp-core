package com.hp.core.webjars.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.core.common.beans.Response;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.webjars.constants.BaseConstant;
import com.hp.core.webjars.model.request.SysUserRequestBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;
import com.hp.core.webjars.service.ISysUserService;

/**
 * 系统用户表控制器
 * @author huangping
 * 2018-08-06
 */
@Controller
@RequestMapping("/SysUserController")
public class SysUserController {

	static Logger log = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/sysUserList")
	public String sysUserList() {
		return "sysManage/sysUser/sysUserList";
	}
	
	/**
	 * 获取登录的用户信息
	 * 
	 * @return
	 * @throws CommonException
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public Response<SysUserResponseBO> getUserInfo(HttpSession session) throws CommonException {
		SysUserResponseBO bo = (SysUserResponseBO) session.getAttribute(BaseConstant.USER_SESSION);
		return new Response<>(bo);
	}
	
	/**
	 * 查询系统用户表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/queryAllSysUser")
	@ResponseBody
	public Response<PageResponse<SysUserResponseBO>> queryAllSysUser(SysUserRequestBO request, PageRequest pageRequest) {
		log.info("queryAllSysUser with request={}, page={}", request, pageRequest);
		PageResponse<SysUserResponseBO> list = sysUserService.querySysUserPageList(request, pageRequest);
		log.info("queryAllSysUser success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return new Response<>(list);
	}

	/**
	 * 保存系统用户表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysUser")
	@ResponseBody
	public Response<Object> saveSysUser(SysUserRequestBO request) {
		log.info("saveSysUser with request={}", request);
		sysUserService.saveSysUser(request);
		log.info("saveSysUser success. with request={}", request);
		return new Response<>();
	}

	/**
	 * 删除系统用户表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysUser")
	@ResponseBody
	public Response<Object> deleteSysUser(Integer id) {
		log.info("deleteSysUser with id={}", id);
		sysUserService.deleteSysUser(id);
		log.info("deleteSysUser success. with id={}", id);
		return new Response<>();
	}

	/**
	 * 根据id，查询系统用户表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysUserById")
	@ResponseBody
	public Response<SysUserResponseBO> querySysUserById(Integer id) {
		log.info("querySysUserById with id={}", id);
		SysUserResponseBO bo = sysUserService.querySysUserById(id);
		log.info("querySysUserById success. with id={}", id);
		return new Response<>(bo);
	}
}
