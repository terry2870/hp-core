/**
 * 
 */
package com.hp.core.echarts.enums;
/**
 * @author huangping
 * 2018年10月9日
 */

import java.util.ArrayList;
import java.util.List;

import com.hp.core.common.beans.ValueTextBean;

public enum TooltipTriggerOnEnum {

	mousemove(1),
	click(2),
	mousemove_click(3) {
		
		@Override
		public String toString() {
			return "mousemove|click";
		}
	},
	none(4);
	
	private Integer value;
	
	private TooltipTriggerOnEnum(Integer value) {
		this.value = value;
	}

	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (TooltipTriggerOnEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}
		return list;
	}
	
	public Integer getValue() {
		return value;
	}
}
