/**
 * 
 */
package com.hp.core.elasticsearch.bean.request;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * Mar 20, 2019
 */
public class BaseSearchRequest extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 796511907930189408L;

	/**
	 * 查询返回的字段
	 */
	private String returnFields;

	public String getReturnFields() {
		return returnFields;
	}

	public void setReturnFields(String returnFields) {
		this.returnFields = returnFields;
	}

	
}
