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
public enum SamplingEnum {

	/**
	 * 取过滤点的平均值
	 */
	average(1),
	
	/**
	 * 取过滤点的最大值
	 */
	max(2),
	
	/**
	 * 取过滤点的最小值
	 */
	min(3),
	
	/**
	 * 取过滤点的和
	 */
	sum(4);
	
	private SamplingEnum(Integer value) {
		this.value = value;
	}
	private Integer value;
	
	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (SamplingEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}
		return list;
	}
	
	public Integer getValue() {
		return value;
	}
}
