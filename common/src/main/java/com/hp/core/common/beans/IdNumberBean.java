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
	
	private Integer num2;
	
	private Integer num3;

	public IdNumberBean() {}
	
	public IdNumberBean(Integer id) {
		this();
		this.id = id;
	}

	public IdNumberBean(Integer id, Integer num) {
		this(id);
		this.num = num;
	}
	
	public IdNumberBean(Integer id, Integer num, Integer num2) {
		this(id, num);
		this.num2 = num2;
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

	public Integer getNum2() {
		return num2;
	}

	public void setNum2(Integer num2) {
		this.num2 = num2;
	}

	public Integer getNum3() {
		return num3;
	}

	public void setNum3(Integer num3) {
		this.num3 = num3;
	}
}
