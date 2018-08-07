package com.hp.core.webjars.service;

import com.hp.core.webjars.model.request.SysRoleMenuRequestBO;
import com.hp.core.webjars.model.response.SysRoleMenuResponseBO;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;

/**
 * 系统角色菜单关联表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysRoleMenuService {

	/**
	 * 保存系统角色菜单关联表
	 * @param request
	 */
	public void saveSysRoleMenu(SysRoleMenuRequestBO request);

	/**
	 * 查询系统角色菜单关联表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<SysRoleMenuResponseBO> querySysRoleMenuPageList(SysRoleMenuRequestBO request, PageRequest pageRequest);

	/**
	 * 删除系统角色菜单关联表
	 * @param id
	 */
	public void deleteSysRoleMenu(Integer id);

	/**
	 * 根据id，查询系统角色菜单关联表
	 * @param id
	 * @return
	 */
	public SysRoleMenuResponseBO querySysRoleMenuById(Integer id);

}
