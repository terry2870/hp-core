/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.TextStyleFontStyleEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class TextStyle extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4550453370619948979L;

	/**
	 * 主标题文字的颜色。
	 * default: '#333'
	 */
	private String color;
	
	/**
	 * 主标题文字字体的风格
	 * default: 'normal'
	 * 可选（normal，italic，oblique）
	 */
	private TextStyleFontStyleEnum fontStyle;
	
	/**
	 * 文字块的宽度
	 */
	private Object width;
	
	/**
	 * 文字块的高度
	 */
	private Object height;

	public String getColor() {
		return color;
	}

	public TextStyle setColor(String color) {
		this.color = color;
		return this;
	}

	public TextStyleFontStyleEnum getFontStyle() {
		return fontStyle;
	}

	public TextStyle setFontStyle(TextStyleFontStyleEnum fontStyle) {
		this.fontStyle = fontStyle;
		return this;
	}

	public Object getWidth() {
		return width;
	}

	public TextStyle setWidth(Object width) {
		this.width = width;
		return this;
	}

	public Object getHeight() {
		return height;
	}

	public TextStyle setHeight(Object height) {
		this.height = height;
		return this;
	}
}
