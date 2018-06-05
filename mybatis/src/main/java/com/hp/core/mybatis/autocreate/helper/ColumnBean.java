/* author hp
 * 创建日期 Aug 16, 2011
 */
package com.hp.core.mybatis.autocreate.helper;

import com.hp.core.common.beans.BaseBean;

public class ColumnBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 37144531799276457L;
	private String columnName;
	private String columnComment;
	private String fieldName;
	private String fieldNameFirstUpper;
	private String jdbcType;
	private String javaType;
	private Integer dataLength;
	private Integer dataScale;
	private String constraintName;
	private String constraintType;

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnComment() {
		return this.columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment == null ? "" : columnComment;
	}

	public Integer getDataLength() {
		return this.dataLength;
	}

	public void setDataLength(Integer dataLength) {
		this.dataLength = dataLength;
	}

	public Integer getDataScale() {
		return this.dataScale;
	}

	public void setDataScale(Integer dataScale) {
		this.dataScale = dataScale;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
	}

	public String getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldNameFirstUpper() {
		return fieldNameFirstUpper;
	}

	public void setFieldNameFirstUpper(String fieldNameFirstUpper) {
		this.fieldNameFirstUpper = fieldNameFirstUpper;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}
}
