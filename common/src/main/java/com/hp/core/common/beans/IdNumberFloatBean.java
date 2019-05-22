/**
 * 
 */
package com.hp.core.common.beans;
/**
 * @author huangping
 * Apr 4, 2019
 */
public class IdNumberFloatBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4242961530795696471L;

	private Integer id;
	
	private Float num;

	public IdNumberFloatBean() {}
	
	public IdNumberFloatBean(Integer id) {
		this();
		this.id = id;
	}

	public IdNumberFloatBean(Integer id, Float num) {
		this(id);
		this.num = num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getNum() {
		return num;
	}

	public void setNum(Float num) {
		this.num = num;
	}
}
