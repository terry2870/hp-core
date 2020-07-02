package com.hp.core.test.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018-05-31
 */
public class ProjectInfo extends BaseBean {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String projectName;

	private Integer projectType;

	private String enterpriseName;

	private String enterpriseContacts;

	private String enterpriseMobile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getEnterpriseContacts() {
		return enterpriseContacts;
	}

	public void setEnterpriseContacts(String enterpriseContacts) {
		this.enterpriseContacts = enterpriseContacts;
	}

	public String getEnterpriseMobile() {
		return enterpriseMobile;
	}

	public void setEnterpriseMobile(String enterpriseMobile) {
		this.enterpriseMobile = enterpriseMobile;
	}

}
