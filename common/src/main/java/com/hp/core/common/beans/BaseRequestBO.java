/**
 * 
 */
package com.hp.core.common.beans;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2016年9月4日 上午12:24:26
 */
public class BaseRequestBO extends BaseBean {

	private static final long serialVersionUID = 7863585169540166691L;

	
	private String queryStartDate;
	private String queryEndDate;
	public String getQueryStartDate() {
		return queryStartDate;
	}
	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}
	public String getQueryEndDate() {
		return queryEndDate;
	}
	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}
}
