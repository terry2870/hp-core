/**
 * 
 */
package com.hp.core.database.bean;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.database.enums.QueryTypeEnum;

/**
 * @author huangping
 * Aug 2, 2019
 */
public class SQLBuilder extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1646858399862430660L;

	/**
	 * 数据库字段名称
	 */
	private String field;
	
	/**
	 * 值
	 */
	private Object value;
	
	/**
     * Java数据类型
     */
    private Class<?> javaType = Object.class;
	
	/**
	 * 运算符
	 */
	private QueryTypeEnum operator = QueryTypeEnum.EQUALS;

	public SQLBuilder() {}
	
	public SQLBuilder(String field, Object value) {
		this.field = field;
		this.value = value;
	}
	
	public SQLBuilder(String field, Object value, QueryTypeEnum operator) {
		this(field, value);
		this.operator = operator;
	}
	
	public SQLBuilder(String field, Object value, Class<?> javaType) {
		this(field, value);
		this.javaType = javaType;
	}

	public SQLBuilder(String field, Object value, Class<?> javaType, QueryTypeEnum operator) {
		this(field, value, javaType);
		this.operator = operator;
	}

	public static SQLBuilder of(String field, Object value) {
		return new SQLBuilder(field, value);
	}
	
	public static SQLBuilder of(String field, Object value, Class<?> javaType) {
		return new SQLBuilder(field, value, javaType);
	}
	
	public static SQLBuilder of(String field, Object value, QueryTypeEnum operator) {
		return new SQLBuilder(field, value, operator);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryTypeEnum getOperator() {
		return operator;
	}

	public void setOperator(QueryTypeEnum operator) {
		this.operator = operator;
	}

}
