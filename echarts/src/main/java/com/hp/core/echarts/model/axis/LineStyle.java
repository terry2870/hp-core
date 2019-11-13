/**
 * 
 */
package com.hp.core.echarts.model.axis;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.LineStyleTypeEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class LineStyle extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5493164445804425635L;
	
	/**
	 * 线的颜色
	 */
	private String color;
	
	/**
	 * 线宽
	 */
	private Integer width;
	
	/**
	 * 线的类型
	 * 默认solid
	 */
	private LineStyleTypeEnum type;

	public String getColor() {
		return color;
	}

	public LineStyle setColor(String color) {
		this.color = color;
		return this;
	}

	public Integer getWidth() {
		return width;
	}

	public LineStyle setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public LineStyleTypeEnum getType() {
		return type;
	}

	public LineStyle setType(LineStyleTypeEnum type) {
		this.type = type;
		return this;
	}
}
