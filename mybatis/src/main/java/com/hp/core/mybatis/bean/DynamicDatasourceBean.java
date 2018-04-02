/**
 * 
 */
package com.hp.core.mybatis.bean;

import java.util.List;

import javax.sql.DataSource;

import com.hp.tools.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DynamicDatasourceBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845239041470232775L;

	
	private List<DataSource> masterDatasource;
	private List<DataSource> slaveDatasource;
	public List<DataSource> getMasterDatasource() {
		return masterDatasource;
	}
	public void setMasterDatasource(List<DataSource> masterDatasource) {
		this.masterDatasource = masterDatasource;
	}
	public List<DataSource> getSlaveDatasource() {
		return slaveDatasource;
	}
	public void setSlaveDatasource(List<DataSource> slaveDatasource) {
		this.slaveDatasource = slaveDatasource;
	}
}
