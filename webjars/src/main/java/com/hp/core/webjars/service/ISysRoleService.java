package com.hp.core.webjars.service;

import com.hp.core.webjars.model.request.SysRoleRequestBO;
import com.hp.core.webjars.model.response.SysRoleResponseBO;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.database.bean.PageRequest;

/**
 * 系统角色表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysRoleService {

	/**
	 * 保存系统角色表
	 * @param request
	 */
	public void saveSysRole(SysRoleRequestBO request);

	/**
	 * 查询系统角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<SysRoleResponseBO> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest);

	/**
	 * 删除系统角色表
	 * @param id
	 */
	public void deleteSysRole(Integer id);

	/**
	 * 根据id，查询系统角色表
	 * @param id
	 * @return
	 */
	public SysRoleResponseBO querySysRoleById(Integer id);

}
