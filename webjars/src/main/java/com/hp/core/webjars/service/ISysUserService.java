package com.hp.core.webjars.service;

import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.webjars.model.request.SysUserRequestBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;

/**
 * 系统用户表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysUserService {

	/**
	 * 保存系统用户表
	 * @param request
	 */
	public void saveSysUser(SysUserRequestBO request);

	/**
	 * 查询系统用户表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<SysUserResponseBO> querySysUserPageList(SysUserRequestBO request, PageRequest pageRequest);

	/**
	 * 删除系统用户表
	 * @param id
	 */
	public void deleteSysUser(Integer id);

	/**
	 * 根据id，查询系统用户表
	 * @param id
	 * @return
	 */
	public SysUserResponseBO querySysUserById(Integer id);
	
	/**
	 * 登录
	 * @param request
	 */
	public SysUserResponseBO login(SysUserRequestBO request);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param oldPwd
	 * @param newPwd
	 */
	public void modifyPwd(Integer userId, String oldPwd, String newPwd);

}
