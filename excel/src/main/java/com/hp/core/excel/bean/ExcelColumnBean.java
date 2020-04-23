/**
 * 
 */
package com.hp.core.excel.bean;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * Apr 26, 2019
 */
public class ExcelColumnBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8452627497390463934L;

	/**
	 * 表头
	 */
	private String title;
	
	/**
	 * 字段名
	 */
	private String field;

	public ExcelColumnBean() {}
	
	public ExcelColumnBean(String title, String field) {
		this.title = title;
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
}
