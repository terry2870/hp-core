package com.hp.core.webjars.service;

import com.hp.core.webjars.model.request.SysMenuRequestBO;
import com.hp.core.webjars.model.response.SysMenuResponseBO;

import java.util.List;

import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;

/**
 * 系统菜单表业务接口定义
 * @author huangping
 * 2018-08-06
 */
public interface ISysMenuService {

	/**
	 * 查询当前用户的所有
	 * @return
	 */
	public List<SysMenuResponseBO> queryAllSysMenu();
	
	/**
	 * 保存系统菜单表
	 * @param request
	 */
	public void saveSysMenu(SysMenuRequestBO request);

	/**
	 * 查询系统菜单表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	public PageResponse<SysMenuResponseBO> querySysMenuPageList(SysMenuRequestBO request, PageRequest pageRequest);

	/**
	 * 删除系统菜单表
	 * @param id
	 */
	public void deleteSysMenu(Integer id);

	/**
	 * 根据id，查询系统菜单表
	 * @param id
	 * @return
	 */
	public SysMenuResponseBO querySysMenuById(Integer id);

}
