/**
 * 
 */
package com.hp.core.echarts.model.series;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.model.Label;

/**
 * @author huangping
 * 2018年10月9日
 */
public class MarkLine extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4963553794078641233L;

	/**
	 * 标线的文本
	 */
	private Label label;

	public Label getLabel() {
		return label;
	}

	public MarkLine setLabel(Label label) {
		this.label = label;
		return this;
	}
}
