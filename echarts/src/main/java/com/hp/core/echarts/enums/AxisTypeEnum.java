/**
 * 
 */
package com.hp.core.echarts.enums;

import java.util.ArrayList;
import java.util.List;

import com.hp.core.common.beans.ValueTextBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public enum AxisTypeEnum {

	/**
	 * 类目轴，适用于离散的类目数据，为该类型时必须通过 data 设置类目数据。
	 */
	category(1),
	
	/**
	 * 数值轴，适用于连续数据。
	 */
	value(2),
	
	/**
	 * 时间轴，适用于连续的时序数据，与数值轴相比时间轴带有时间的格式化，在刻度计算上也有所不同，例如会根据跨度的范围来决定使用月，星期，日还是小时范围的刻度
	 */
	time(3),
	
	/**
	 * 对数轴。适用于对数数据。
	 */
	log(4);
	
	private AxisTypeEnum(Integer value2) {
		this.value2 = value2;
	}
	private Integer value2;
	
	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (AxisTypeEnum e : values()) {
			list.add(new ValueTextBean(e.getValue2(), e.name()));
		}
		return list;
	}

	public Integer getValue2() {
		return value2;
	}
	
}
