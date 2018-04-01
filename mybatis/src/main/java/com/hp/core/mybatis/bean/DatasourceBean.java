/**
 * 
 */
package com.hp.core.mybatis.bean;

import java.util.List;

import com.hp.tools.common.beans.BaseBean;

/**
 * 动态数据库基础bean
 * @author huangping
 * 2018年4月1日 下午10:57:45
 */
public class DatasourceBean extends BaseBean {

	private static final long serialVersionUID = 381003465978823718L;

	
	private String alias;
	private String databaseName;
	private List<String> masterUrls;
	private List<String> slaveUrls;
	private boolean defaultDatabase = false;
	private List<String> daos;
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public List<String> getMasterUrls() {
		return masterUrls;
	}
	public void setMasterUrls(List<String> masterUrls) {
		this.masterUrls = masterUrls;
	}
	public List<String> getSlaveUrls() {
		return slaveUrls;
	}
	public void setSlaveUrls(List<String> slaveUrls) {
		this.slaveUrls = slaveUrls;
	}
	public List<String> getDaos() {
		return daos;
	}
	public void setDaos(List<String> daos) {
		this.daos = daos;
	}
	public boolean isDefaultDatabase() {
		return defaultDatabase;
	}
	public void setDefaultDatabase(boolean defaultDatabase) {
		this.defaultDatabase = defaultDatabase;
	}
}
