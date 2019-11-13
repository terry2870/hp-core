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
public class MarkPoint extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -518859385671671062L;

	/**
	 * 标记的图形
	 */
	private String symbol;
	
	/**
	 * 标注的文本
	 */
	private Label label;

	public String getSymbol() {
		return symbol;
	}

	public MarkPoint setSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public Label getLabel() {
		return label;
	}

	public MarkPoint setLabel(Label label) {
		this.label = label;
		return this;
	}
}
