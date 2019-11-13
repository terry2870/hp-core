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
public enum TooltipTriggerEnum {

	/**
	 * 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用
	 */
	item(1),
	
	/**
	 * 坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用
	 */
	axis(2),
	
	/**
	 * 什么都不触发
	 */
	none(3);
	
	private Integer value;
	
	private TooltipTriggerEnum(Integer value) {
		this.value = value;
	}

	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (TooltipTriggerEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}
		return list;
	}
	
	public Integer getValue() {
		return value;
	}
}
