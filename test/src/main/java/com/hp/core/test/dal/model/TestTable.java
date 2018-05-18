/**
 * 
 */
package com.hp.core.test.dal.model;


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

	private Integer id;
	private String title;
	private String simplified;
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
}
