/**
 * 
 */
package com.hp.core.common.beans;
/**
 * @author huangping
 * Apr 17, 2019
 */
public class IdNameBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6462809094134958842L;

	
	private Integer id;
	
	private String name;
	
	public IdNameBean() {}
	
	public IdNameBean(Integer id) {
		this();
		this.id = id;
	}

	public IdNameBean(Integer id, String name) {
		this(id);
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
