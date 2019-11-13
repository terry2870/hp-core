/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class LegendData extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6556661663064802828L;

	/**
	 * 图例项的名称，应等于某系列的name值（如果是饼图，也可以是饼图单个数据的 name）
	 */
	private String name;
	
	/**
	 * 图例项的 icon
	 */
	private String icon;
	
	/**
	 * 图例项的文本样式
	 */
	private TextStyle textStyle;
	
	public LegendData(String name) {
		this.name = name;
	}
	
	public LegendData(String name, String icon) {
		this(name);
		this.icon = icon;
	}

	public LegendData(String name, String icon, TextStyle textStyle) {
		this(name, icon);
		this.textStyle = textStyle;
	}

	public String getName() {
		return name;
	}

	public LegendData setName(String name) {
		this.name = name;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public LegendData setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public LegendData setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}
}
