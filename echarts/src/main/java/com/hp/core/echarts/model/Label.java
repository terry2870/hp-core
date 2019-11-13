/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.FontStyleEnum;
import com.hp.core.echarts.enums.FontWeightEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Label extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891206937959575131L;

	/**
	 * 是否显示标签
	 * 默认false
	 */
	private Boolean show;
	
	/**
	 * 标签的位置
	 */
	private Object position;
	
	/**
	 * 文字的颜色
	 */
	private String color;
	
	/**
	 * 文字样式
	 */
	private FontStyleEnum fontStyle = FontStyleEnum.normal;
	
	/**
	 * 文字大小
	 */
	private FontWeightEnum fontWeight = FontWeightEnum.normal;
	
	/**
	 * 字体
	 */
	private String fontFamily;
	
	/**
	 * 字体大小
	 */
	private Integer fontSize;
	

	public Boolean getShow() {
		return show;
	}

	public Label setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public Object getPosition() {
		return position;
	}

	public Label setPosition(Object position) {
		this.position = position;
		return this;
	}

	public String getColor() {
		return color;
	}

	public Label setColor(String color) {
		this.color = color;
		return this;
	}

	public FontStyleEnum getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(FontStyleEnum fontStyle) {
		this.fontStyle = fontStyle;
	}

	public FontWeightEnum getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(FontWeightEnum fontWeight) {
		this.fontWeight = fontWeight;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}
	
}
