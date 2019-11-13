/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class DataZoom extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7584626221373800686L;

	/**
	 * 是否显示
	 * 默认true
	 */
	private Boolean show;

	public DataZoom() {}
	
	public DataZoom(Boolean show) {
		this.show = show;
	}

	public Boolean getShow() {
		return show;
	}

	public DataZoom setShow(Boolean show) {
		this.show = show;
		return this;
	}
}
