package com.hp.core.webjars.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysRoleMenu extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	private Integer id;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 菜单ID
	 */
	private Integer menuId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
