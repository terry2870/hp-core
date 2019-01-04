/**
 * 
 */
package com.hp.core.database.bean;

import com.hp.core.common.beans.BaseBean;

/**
 * 存放接口或类中泛型对象
 * @author huangping
 * 2018年5月22日
 */
public class GenericParadigmBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8960852660677843117L;

	/**
	 * model对象的class
	 */
	private Class<?> targetModelClassName;
	
	/**
	 * 主键的对象class
	 */
	private Class<?> primaryKeyClassName;
	
	public GenericParadigmBean(Class<?> targetModelClassName) {
		this(targetModelClassName, Integer.class);
	}
	
	public GenericParadigmBean(Class<?> targetModelClassName, Class<?> primaryKeyClassName) {
		this.targetModelClassName = targetModelClassName;
		this.primaryKeyClassName = primaryKeyClassName;
	}

	public Class<?> getTargetModelClassName() {
		return targetModelClassName;
	}

	public void setTargetModelClassName(Class<?> targetModelClassName) {
		this.targetModelClassName = targetModelClassName;
	}

	public Class<?> getPrimaryKeyClassName() {
		return primaryKeyClassName;
	}

	public void setPrimaryKeyClassName(Class<?> primaryKeyClassName) {
		this.primaryKeyClassName = primaryKeyClassName;
	}
}
