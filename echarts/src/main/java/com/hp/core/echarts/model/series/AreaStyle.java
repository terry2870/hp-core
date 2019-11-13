/**
 * 
 */
package com.hp.core.echarts.model.series;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * May 9, 2019
 */
public class AreaStyle extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4803920475056717335L;

	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 阴影颜色
	 */
	private String shadowColor;
	
	/**
	 * 图形阴影的模糊大小
	 */
	private Integer shadowBlur;
	
	/**
	 * 图形透明度
	 */
	private Float opacity;
	
	public AreaStyle() {}
	
	public AreaStyle(String color, String shadowColor) {
		this.color = color;
		this.shadowColor = shadowColor;
	}
	
	public AreaStyle(String color, String shadowColor, Integer shadowBlur) {
		this(color, shadowColor);
		this.shadowBlur = shadowBlur;
	}
	
	public AreaStyle(String color, String shadowColor, Integer shadowBlur, Float opacity) {
		this(color, shadowColor, shadowBlur);
		this.opacity = opacity;
	}

	public String getColor() {
		return color;
	}

	public AreaStyle setColor(String color) {
		this.color = color;
		return this;
	}

	public Integer getShadowBlur() {
		return shadowBlur;
	}

	public AreaStyle setShadowBlur(Integer shadowBlur) {
		this.shadowBlur = shadowBlur;
		return this;
	}

	public String getShadowColor() {
		return shadowColor;
	}

	public AreaStyle setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
		return this;
	}

	public Float getOpacity() {
		return opacity;
	}

	public AreaStyle setOpacity(Float opacity) {
		this.opacity = opacity;
		return this;
	}
}
