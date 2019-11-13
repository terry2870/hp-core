/**
 * 
 */
package com.hp.core.echarts.model.axis;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.model.TextStyle;

/**
 * @author huangping
 * 2018年10月9日
 */
public class AxisData extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 491624463145952638L;

	/**
	 * 单个类目名称
	 */
	private Object value;
	
	/**
	 * 类目标签的文字样式
	 */
	private TextStyle textStyle;
	
	public AxisData() {}
	
	public AxisData(Object value) {
		this.value = value;
	}
	
	public AxisData(Object value, TextStyle textStyle) {
		this(value);
		this.textStyle = textStyle;
	}

	public Object getValue() {
		return value;
	}

	public AxisData setValue(Object value) {
		this.value = value;
		return this;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public AxisData setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}
}
