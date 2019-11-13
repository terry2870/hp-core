/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class MagicType extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5486386224677939855L;

	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	public MagicType() {}

	public MagicType(Boolean show) {
		this.show = show;
	}

	public Boolean getShow() {
		return show;
	}

	public MagicType setShow(Boolean show) {
		this.show = show;
		return this;
	}
}
