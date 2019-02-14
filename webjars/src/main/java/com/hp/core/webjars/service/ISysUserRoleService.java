/**
 * 
 */
package com.hp.core.webjars.service;

import java.util.List;

/**
 * @author huangping
 * 2016年8月28日 下午4:23:37
 */
public interface ISysUserRoleService {

	/**
	 * 查询该用户的角色
	 * @param userId
	 * @return
	 */
	public List<Integer> selectRoleByUserId(Integer userId);
	
	/**
	 * 插入用户的角色关系
	 * @param userId
	 * @param roleIdList
	 */
	public void insertUserRole(Integer userId, List<Integer> roleIdList);
}
