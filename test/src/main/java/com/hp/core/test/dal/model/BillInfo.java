package com.hp.core.test.dal.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.hp.core.common.beans.BaseBean;

public class BillInfo extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5218103022405672603L;

	@Id
	private Integer id;

	@Column(updatable = false)
	private Integer projectId;

	private String enterpriseName;

	@Column(updatable = false)
	private String expressContacts;

	private String expressContactsMobile;

	private Integer billType;

	private Integer billMoney;

	private Integer billCheckStatus;

	private Integer billStatus;

	private String expressCode;

	private Integer status;

	private Integer createUserId;

	private Integer createTime;
	
	@Transient
	private String createTimeJoin;

	private Integer updateTime;
	
	@Transient
	private String queryYear;
	
	private Integer billCompany;
	
	@Transient
	private String billCompanyStr;
	
	@Transient
	private String isIndex;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
	}

	public String getExpressContacts() {
		return expressContacts;
	}

	public void setExpressContacts(String expressContacts) {
		this.expressContacts = expressContacts == null ? null : expressContacts.trim();
	}

	public String getExpressContactsMobile() {
		return expressContactsMobile;
	}

	public void setExpressContactsMobile(String expressContactsMobile) {
		this.expressContactsMobile = expressContactsMobile == null ? null : expressContactsMobile.trim();
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getBillMoney() {
		return billMoney;
	}

	public void setBillMoney(Integer billMoney) {
		this.billMoney = billMoney;
	}

	public Integer getBillCheckStatus() {
		return billCheckStatus;
	}

	public void setBillCheckStatus(Integer billCheckStatus) {
		this.billCheckStatus = billCheckStatus;
	}

	public Integer getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(Integer billStatus) {
		this.billStatus = billStatus;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode == null ? null : expressCode.trim();
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

	public String getCreateTimeJoin() {
		return createTimeJoin;
	}

	public void setCreateTimeJoin(String createTimeJoin) {
		this.createTimeJoin = createTimeJoin;
	}

	public String getQueryYear() {
		return queryYear;
	}

	public void setQueryYear(String queryYear) {
		this.queryYear = queryYear;
	}

	public Integer getBillCompany() {
		return billCompany;
	}

	public void setBillCompany(Integer billCompany) {
		this.billCompany = billCompany;
	}

	public String getBillCompanyStr() {
		return billCompanyStr;
	}

	public void setBillCompanyStr(String billCompanyStr) {
		this.billCompanyStr = billCompanyStr;
	}

	public String getIsIndex() {
		return isIndex;
	}

	public void setIsIndex(String isIndex) {
		this.isIndex = isIndex;
	}

}