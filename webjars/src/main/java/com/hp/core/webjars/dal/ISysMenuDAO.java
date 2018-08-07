package com.hp.core.webjars.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 对应的dao接口
 * @author huangping
 * 2018-08-06
 */
import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysMenu;

public interface ISysMenuDAO extends BaseMapper<SysMenu> {

	/**
	 * 查询该用户的菜单
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
}
