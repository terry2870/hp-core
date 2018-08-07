package com.hp.core.webjars.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysRole extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Id
	private Integer id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleInfo;

	/**
	 * 角色状态
	 */
	private Integer status;

	/**
	 * 创建者ID
	 */
	private Integer createUserId;

	/**
	 * 创建日期
	 */
	private Integer createTime;

	/**
	 * 更新时间
	 */
	private Integer updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
	}

}
