package com.hp.core.webjars.service;

import com.hp.core.webjars.model.request.SysUserRoleRequestBO;
import com.hp.core.webjars.model.response.SysUserRoleResponseBO;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;

/**
 * 用户角色表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysUserRoleService {

	/**
	 * 保存用户角色表
	 * @param request
	 */
	public void saveSysUserRole(SysUserRoleRequestBO request);

	/**
	 * 查询用户角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<SysUserRoleResponseBO> querySysUserRolePageList(SysUserRoleRequestBO request, PageRequest pageRequest);

	/**
	 * 删除用户角色表
	 * @param id
	 */
	public void deleteSysUserRole(Integer id);

	/**
	 * 根据id，查询用户角色表
	 * @param id
	 * @return
	 */
	public SysUserRoleResponseBO querySysUserRoleById(Integer id);

}
