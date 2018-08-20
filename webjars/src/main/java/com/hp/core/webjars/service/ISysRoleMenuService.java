/**
 * 
 */
package com.hp.core.webjars.service;

import java.util.List;

/**
 * @author huangping
 * 2016年9月11日 下午9:30:28
 */
public interface ISysRoleMenuService {

	/**
	 * 根据角色id，查询该角色的菜单
	 * @param roleId
	 * @return
	 */
	public List<Integer> selectMenuByRoleId(Integer roleId);
	
	/**
	 * 根据菜单id，查询
	 * @param roleId
	 * @return
	 */
	//List<SysRoleMenuResponseBO> selectByMenuId(Integer menuId) throws Exception;
	
	/**
	 * 批量插入角色菜单
	 * @param roleId
	 * @param menuIds
	 */
	public void saveSysRoleMenu(Integer roleId, String menuIds);
}
