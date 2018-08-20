package com.hp.core.webjars.model.request;

import com.hp.core.common.beans.BaseRequestBO;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysUserRequestBO extends BaseRequestBO {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 登录名
	 */
	private String loginName;

	/**
	 * 登录密码
	 */
	private String loginPwd;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 固定电话
	 */
	private String phoneNumber;

	/**
	 * 用户地址
	 */
	private String address;

	/**
	 * email
	 */
	private String email;

	/**
	 * 用户状态（1-正常；2-已删除；3-无效）
	 */
	private Integer status;

	/**
	 * 创建者ID
	 */
	private Integer createUserId;

	/**
	 * 最后一次登录时间
	 */
	private Integer lastLoginTime;

	/**
	 * 创建时间
	 */
	private Integer createTime;

	/**
	 * 更新时间
	 */
	private Integer updateTime;

	/**
	 * 用户身份（1-超级管理员；2-店长；3-店员）
	 */
	private Integer identity;
	
	private String checkCode;
	
	private String roleIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

}
