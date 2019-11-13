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
public enum TextStyleFontStyleEnum {

	normal(1),
	italic(2),
	oblique(3);
	
	private Integer value;
	
	private TextStyleFontStyleEnum(Integer value) {
		this.value = value;
	}

	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static List<ValueTextBean> toJSON() {
		List<ValueTextBean> list = new ArrayList<>();
		for (TextStyleFontStyleEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}
		return list;
	}
	
	public Integer getValue() {
		return value;
	}
}
