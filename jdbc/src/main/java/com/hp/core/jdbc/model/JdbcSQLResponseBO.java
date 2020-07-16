/**
 * 
 */
package com.hp.core.jdbc.model;

import java.util.List;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * Jul 13, 2020
 */
public class JdbcSQLResponseBO extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7145342367361287083L;
	
	private String sql;

	private List<Object> params;
	
	private List<String> whereSQL;

	public JdbcSQLResponseBO() {}
	
	public JdbcSQLResponseBO(List<Object> params, List<String> whereSQL) {
		this.params = params;
		this.whereSQL = whereSQL;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
	
	public Object[] getParamsArray() {
		if (this.params == null) {
			return null;
		}
		return this.params.toArray(new Object[] {});
	}

	public List<String> getWhereSQL() {
		return whereSQL;
	}

	public void setWhereSQL(List<String> whereSQL) {
		this.whereSQL = whereSQL;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
