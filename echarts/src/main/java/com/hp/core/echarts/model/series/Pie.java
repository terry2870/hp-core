/**
 * 
 */
package com.hp.core.echarts.model.series;

import java.util.Collection;

import com.hp.core.echarts.enums.SeriesTypeEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Pie extends Series<Pie> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	/**
	 * 圆心坐标，支持绝对值（px）和百分比，百分比计算min(width, height) * 50%
	 */
	private Object[] center;
	
	/**
	 * 饼图的半径
	 */
	private Object radius;
	
	public Pie() {
		setType(SeriesTypeEnum.pie);
	}
	
	public Pie(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.pie, name, data);
	}
	
	public Pie(String name, Object... values) {
		super(SeriesTypeEnum.pie, name, values);
	}

	public Object[] getCenter() {
		return center;
	}

	public Pie setCenter(Object[] center) {
		this.center = center;
		return this;
	}

	public Object getRadius() {
		return radius;
	}

	public Pie setRadius(Object radius) {
		this.radius = radius;
		return this;
	}
}
