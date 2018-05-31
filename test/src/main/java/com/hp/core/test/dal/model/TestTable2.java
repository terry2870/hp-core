package com.hp.core.test.dal.model;

import javax.persistence.Id;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年5月14日
 */
public class TestTable2 extends BaseBean {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	private Integer id;
	
	/**
	 * title
	 */
	private String title;

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
}
