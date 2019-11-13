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
public class MarkArea extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3510326499621109985L;

	/**
	 * 标域文本配置
	 */
	private Label label;

	public Label getLabel() {
		return label;
	}

	public MarkArea setLabel(Label label) {
		this.label = label;
		return this;
	}
}
