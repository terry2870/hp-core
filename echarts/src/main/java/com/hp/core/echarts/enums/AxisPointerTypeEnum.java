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
public enum AxisPointerTypeEnum {

	/**
	 * 直线指示器
	 */
	line(1),
	
	/**
	 * 阴影指示器
	 */
	shadow(2),
	
	/**
	 * 无指示器
	 */
	none(3),
	
	/**
	 * 十字准星指示器
	 */
	cross(4);
	
	private AxisPointerTypeEnum(Integer value) {
		this.value = value;
	}
	private Integer value;
	
	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (AxisPointerTypeEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}
		return list;
	}
	
	public Integer getValue() {
		return value;
	}
}
