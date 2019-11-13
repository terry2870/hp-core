/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class DataView extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8727721826202766584L;

	/**
	 * 是否显示
	 * 默认true
	 */
	private Boolean show;
	
	/**
	 * 显示文字
	 * 默认值"数据视图"
	 */
	private String title;
	
	public DataView() {}

	public DataView(Boolean show) {
		this.show = show;
	}

	public Boolean getShow() {
		return show;
	}

	public DataView setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public DataView setTitle(String title) {
		this.title = title;
		return this;
	}
}
