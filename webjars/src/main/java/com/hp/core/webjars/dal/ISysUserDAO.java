package com.hp.core.webjars.dal;

import org.apache.ibatis.annotations.Param;

import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysUser;

public interface ISysUserDAO extends BaseMapper<SysUser, Integer> {
	
	/**
	 * 登录
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public SysUser selectUserByLoginNameAndPwd(@Param("loginName") String loginName, @Param("loginPwd") String loginPwd);
}
