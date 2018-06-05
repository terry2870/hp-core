/* author hp
 * 创建日期 Aug 15, 2011
 */
package com.hp.core.mybatis.autocreate.helper;

import java.util.List;

import com.hp.core.common.beans.BaseBean;

public class TableBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1161971194228301962L;
	private String tableName;
	private String modelName;
	private String modelNameFirstLow;
	private String tableComment;
	private List<ColumnBean> columnList;
	private String primaryKey;
	private String primaryKeyFirstUpper;
	private String status;

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return this.tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment == null ? "" : tableComment;
	}
	
	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public List<ColumnBean> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<ColumnBean> columnList) {
		this.columnList = columnList;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelNameFirstLow() {
		return modelNameFirstLow;
	}

	public void setModelNameFirstLow(String modelNameFirstLow) {
		this.modelNameFirstLow = modelNameFirstLow;
	}

	public String getPrimaryKeyFirstUpper() {
		return primaryKeyFirstUpper;
	}

	public void setPrimaryKeyFirstUpper(String primaryKeyFirstUpper) {
		this.primaryKeyFirstUpper = primaryKeyFirstUpper;
	}

}
