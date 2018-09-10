package com.hp.core.webjars.dal;

import org.apache.ibatis.annotations.Param;

/**
 * 对应的dao接口
 * @author huangping
 * 2018-08-06
 */
import com.hp.core.mybatis.mapper.BaseMapper;
import com.hp.core.webjars.dal.model.SysUser;

public interface ISysUserDAO extends BaseMapper<SysUser> {
	
	/**
	 * 登录
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public SysUser selectUserByLoginNameAndPwd(@Param("loginName") String loginName, @Param("loginPwd") String loginPwd);
}
