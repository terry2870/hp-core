/**
 * 
 */
package com.hp.core.mybatis.bean;

import com.hp.tools.common.beans.BaseBean;

/**
 * 当前执行的dao的类属性
 * @author huangping
 * 2018年4月11日
 */
public class DAOInterfaceInfoBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4162275762456111854L;
	
	/**
	 * dao的类名称
	 */
	private String mapperNamespace;
	
	/**
	 * 执行的方法
	 */
	private String statementId;

	public String getMapperNamespace() {
		return mapperNamespace;
	}

	public void setMapperNamespace(String mapperNamespace) {
		this.mapperNamespace = mapperNamespace;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

}
