package com.hp.core.webjars.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

public class SysRoleMenu extends BaseBean {
	private static final long serialVersionUID = -2910964482055620993L;

	@Id
	private Integer id;
	private Integer roleId;

	private Integer menuId;

	/**
	 * @param roleId
	 * @param menuId
	 */
	public SysRoleMenu(Integer roleId, Integer menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}

	/**
	 * 
	 */
	public SysRoleMenu() {
		super();
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}