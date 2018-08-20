package com.hp.core.webjars.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

public class SysUserRole extends BaseBean {
	private static final long serialVersionUID = -7929178352943087223L;

	@Id
	private Integer id;
	
	private Integer userId;

	private Integer roleId;

	/**
	 * @param userId
	 * @param roleId
	 */
	public SysUserRole(Integer userId, Integer roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

	/**
	 * 
	 */
	public SysUserRole() {
		super();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}