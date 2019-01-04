/**
 * 
 */
package com.hp.core.database.bean;

import java.sql.JDBCType;

import javax.persistence.GenerationType;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.database.enums.QueryTypeEnum;

/**
 * @author huangping
 * 2018年5月23日
 */
public class DynamicColumnBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9101732846630330481L;

	/**
     * 数据库字段名称
     */
    private String columnName;
    
    /**
     * java对象属性名称
     */
    private String fieldName;
    
    /**
     * Java数据类型
     */
    private Class<?> javaType;
    
    /**
     * 数据库数据类型
     */
    private JDBCType jdbcType;
    
    /**
     * 主键生成策略
     */
    private GenerationType generationType = GenerationType.AUTO;
    
    /**
     * 匹配方式
     */
    private QueryTypeEnum queryType = QueryTypeEnum.EQUALS;
    
    /**
     * 是否持久化
     */
    private boolean persistence = true;
    /**
     * 是否为主键
     */
    private boolean primaryKey = false;
    
    /**
     * 是否是唯一键
     */
    private boolean uniqueKey = false;
    
    /**
     * 字段长度
     */
    private int length = 0;
    
    /**
     * 可否为空
     */
    private boolean nullable = true;
    
    /**
     * 是否参与插入
     */
    private boolean insertable = true;
    
    /**
     * 是否参与更新
     */
    private boolean updatable = true;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public JDBCType getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(JDBCType jdbcType) {
		this.jdbcType = jdbcType;
	}

	public GenerationType getGenerationType() {
		return generationType;
	}

	public void setGenerationType(GenerationType generationType) {
		this.generationType = generationType;
	}

	public boolean isPersistence() {
		return persistence;
	}

	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(boolean uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public QueryTypeEnum getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryTypeEnum queryType) {
		this.queryType = queryType;
	}
}
