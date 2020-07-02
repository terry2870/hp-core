/**
 * 
 */
package com.hp.core.test.es.model.request;

import com.hp.core.elasticsearch.bean.request.BaseSearchRequest;

/**
 * @author huangping
 * Jun 22, 2020
 */
public class ProjectSearchRequest extends BaseSearchRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6426782665016586952L;

	private Integer id;

	private String projectName;

	private Integer projectType;

	private String enterpriseName;

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
}
