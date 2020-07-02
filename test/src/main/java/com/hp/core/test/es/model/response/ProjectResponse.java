/**
 * 
 */
package com.hp.core.test.es.model.response;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.hp.core.elasticsearch.bean.response.BaseSearchResponse;

/**
 * @author huangping
 * Jun 22, 2020
 */
@Document(indexName = "project_info")
public class ProjectResponse extends BaseSearchResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1451560516614816825L;

	@Id
	private String id;

	//@Field(type = FieldType.Keyword, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
	private String projectName;

	private Integer projectType;

	//@Field(type = FieldType.Keyword, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
	private String enterpriseName;

	@Field(type = FieldType.Text , analyzer = "ik_smart")
	private String enterpriseContacts;

	@Field(type = FieldType.Keyword)
	private String enterpriseMobile;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getEnterpriseMobile() {
		return enterpriseMobile;
	}

	public void setEnterpriseMobile(String enterpriseMobile) {
		this.enterpriseMobile = enterpriseMobile;
	}

	public String getEnterpriseContacts() {
		return enterpriseContacts;
	}

	public void setEnterpriseContacts(String enterpriseContacts) {
		this.enterpriseContacts = enterpriseContacts;
	}
}
