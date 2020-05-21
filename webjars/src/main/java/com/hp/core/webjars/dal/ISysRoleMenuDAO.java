package com.hp.core.webjars.dal;

import java.util.List;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysRoleMenu;

public interface ISysRoleMenuDAO extends BaseMapper<SysRoleMenu, Integer> {
	
	/**
	 * 根据角色id，查询
	 * @param roleId
	 * @return
	 */
	public List<Integer> selectMenuByRoleId(Integer roleId);
	
	/**
	 * 删除该角色的菜单
	 * @param roleId
	 */
	public void deleteByRoleId(Integer roleId);
	
	/**
	 * 根据菜单id，查询
	 * @param roleId
	 * @return
	 */
	List<SysRoleMenu> selectByMenuId(Integer menuId);
		
	/**
	 * 查询该用户的菜单
	 * @param userId
	 * @return
	 */
	List<Integer> selectByUserId(Integer userId);
}