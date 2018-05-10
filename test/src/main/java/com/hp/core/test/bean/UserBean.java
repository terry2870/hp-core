/**
 * 
 */
package com.hp.core.test.bean;

import com.hp.core.common.beans.BaseBean;

/**
 * @author ping.huang
 * 2017年5月15日
 */
public class UserBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2825846414584459135L;

	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
