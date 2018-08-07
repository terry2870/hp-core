package com.hp.core.webjars.model.response;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysUserRoleResponseBO extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 角色id
	 */
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
