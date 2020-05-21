package com.hp.core.webjars.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysMenu;

public interface ISysMenuDAO extends BaseMapper<SysMenu, Integer> {

	/**
	 * 查询该用户的菜单（非超级管理员）
	 * @param userId
	 * @return
	 */
	public List<Integer> selectByUserId(@Param("userId") Integer userId);
	
	/**
	 * 查询菜单，向上递归
	 * @param menuIds
	 * @return
	 */
	public List<SysMenu> selectSysMenu(@Param("menuIds") String menuIds);
	
	/**
	 * 查询超级管理员看到的菜单
	 * @return
	 */
	public List<SysMenu> selectMenuForSuperAdmin();
}
