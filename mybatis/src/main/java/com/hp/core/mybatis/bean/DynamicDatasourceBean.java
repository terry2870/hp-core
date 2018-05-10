/**
 * 
 */
package com.hp.core.mybatis.bean;

import javax.sql.DataSource;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DynamicDatasourceBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845239041470232775L;

	
	private DataSource masterDatasource;
	private DataSource slaveDatasource;
	
	public DynamicDatasourceBean() {}

	public DynamicDatasourceBean(DataSource masterDatasource) {
		this.masterDatasource = masterDatasource;
	}
	
	public DynamicDatasourceBean(DataSource masterDatasource, DataSource slaveDatasource) {
		this(masterDatasource);
		this.slaveDatasource = slaveDatasource;
	}
	public DataSource getMasterDatasource() {
		return masterDatasource;
	}
	public void setMasterDatasource(DataSource masterDatasource) {
		this.masterDatasource = masterDatasource;
	}
	public DataSource getSlaveDatasource() {
		return slaveDatasource;
	}
	public void setSlaveDatasource(DataSource slaveDatasource) {
		this.slaveDatasource = slaveDatasource;
	}
	
}
