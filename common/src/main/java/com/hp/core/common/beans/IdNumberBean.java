/**
 * 
 */
package com.hp.core.common.beans;
/**
 * @author huangping
 * Apr 4, 2019
 */
public class IdNumberBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242961530795696471L;

	private Integer id;
	
	private Integer num;

	public IdNumberBean() {}
	
	public IdNumberBean(Integer id) {
		this();
		this.id = id;
	}

	public IdNumberBean(Integer id, Integer num) {
		this(id);
		this.num = num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
