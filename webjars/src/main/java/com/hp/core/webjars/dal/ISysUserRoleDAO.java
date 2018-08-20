package com.hp.core.webjars.dal;

import java.util.List;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysUserRole;

public interface ISysUserRoleDAO extends BaseMapper<SysUserRole> {
	
	
	/**
	 * 根据用户id，查询
	 * @param userId
	 * @return
	 */
	public List<Integer> selectRoleByUserId(Integer userId);
	
	/**
	 * 删除该用户的角色关系
	 * @param userId
	 * @return
	 */
	public void deleteByUserId(Integer userId);
}