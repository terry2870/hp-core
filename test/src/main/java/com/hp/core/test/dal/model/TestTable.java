/**
 * 
 */
package com.hp.core.test.dal.model;


import javax.persistence.Id;
import javax.persistence.Transient;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年5月14日
 */
public class TestTable extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -463188118297996495L;

	@Id
	private Integer id;
	private String title;
	
	private String simplified;
	
	@Transient
	private String str1111;
	
	private String testName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSimplified() {
		return simplified;
	}
	public void setSimplified(String simplified) {
		this.simplified = simplified;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
}
