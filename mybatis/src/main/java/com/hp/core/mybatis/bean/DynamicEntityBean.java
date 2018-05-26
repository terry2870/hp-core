/**
 * 
 */
package com.hp.core.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping 2018年5月23日
 */
public class DynamicEntityBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5136441941368303442L;

	/**
	 * 实体的类
	 */
	private Class<?> className;

	/**
	 * 数据库表名称
	 */
	private String tableName;
	/**
	 * 主键数据库字段名称
	 */
	private String primaryKeyColumnName;
	/**
	 * 主键映射的model对象名称
	 */
	private String primaryKeyFieldName;

	/**
	 * 主键java类型
	 */
	private Class<?> primaryKeyJavaType;

	/**
	 * 主键JDBC类型
	 */
	private JdbcType primaryKeyJdbcType;

	/**
	 * select查询的字段
	 */
	private String selectColumns;

	/**
	 * select查询的字段
	 */
	private List<String> selectColumnsList;

	/**
	 * insert语句用到的字段
	 */
	private String insertColumns;

	/**
	 * insert语句用到的字段
	 */
	private List<String> insertColumnsList;

	/**
	 * update语句用到的字段
	 */
	private String updateColumns;

	/**
	 * update语句用到的字段
	 */
	private List<String> updateColumnsList;

	/**
	 * 所有的字段
	 */
	private List<DynamicColumnBean> columns;

	/**
	 * 处理所有字段
	 */
	public void dealColumns() {
		if (CollectionUtils.isEmpty(columns)) {
			return;
		}

		selectColumnsList = new ArrayList<>();
		insertColumnsList = new ArrayList<>();
		updateColumnsList = new ArrayList<>();
		for (DynamicColumnBean column : columns) {
			if (!column.isPersistence()) {
				// 非持久化，退出
				continue;
			}
			selectColumnsList.add(column.getColumnName());
			if (column.isInsertable()) {
				insertColumnsList.add(column.getColumnName());
			}
			if (column.isUpdatable()) {
				updateColumnsList.add(column.getColumnName());
			}

			if (CollectionUtils.isNotEmpty(selectColumnsList)) {
				selectColumns = StringUtils.join(selectColumnsList, ", ");
			}
			if (CollectionUtils.isNotEmpty(insertColumnsList)) {
				insertColumns = StringUtils.join(insertColumnsList, ", ");
			}
			if (CollectionUtils.isNotEmpty(updateColumnsList)) {
				updateColumns = StringUtils.join(updateColumnsList, ", ");
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKeyFieldName() {
		return primaryKeyFieldName;
	}

	public void setPrimaryKeyFieldName(String primaryKeyFieldName) {
		this.primaryKeyFieldName = primaryKeyFieldName;
	}

	public Class<?> getPrimaryKeyJavaType() {
		return primaryKeyJavaType;
	}

	public void setPrimaryKeyJavaType(Class<?> primaryKeyJavaType) {
		this.primaryKeyJavaType = primaryKeyJavaType;
	}

	public JdbcType getPrimaryKeyJdbcType() {
		return primaryKeyJdbcType;
	}

	public void setPrimaryKeyJdbcType(JdbcType primaryKeyJdbcType) {
		this.primaryKeyJdbcType = primaryKeyJdbcType;
	}

	public List<DynamicColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<DynamicColumnBean> columns) {
		this.columns = columns;
	}

	public String getPrimaryKeyColumnName() {
		return primaryKeyColumnName;
	}

	public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
		this.primaryKeyColumnName = primaryKeyColumnName;
	}

	public Class<?> getClassName() {
		return className;
	}

	public void setClassName(Class<?> className) {
		this.className = className;
	}

	public String getSelectColumns() {
		return selectColumns;
	}

	public String getInsertColumns() {
		return insertColumns;
	}

	public List<String> getInsertColumnsList() {
		return insertColumnsList;
	}

	public String getUpdateColumns() {
		return updateColumns;
	}

	public List<String> getUpdateColumnsList() {
		return updateColumnsList;
	}

	public List<String> getSelectColumnsList() {
		return selectColumnsList;
	}

}
