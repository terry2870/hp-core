/**
 * 
 */
package com.hp.core.echarts.enums;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.core.common.beans.ValueTextBean;

/**
 * @author huangping 2018年10月9日
 */
public enum SeriesTypeEnum {
	line(1, "折线图"), // 折线图.........................
	lines(2, "线图"), // 线图,用于带有起点和终点信息的线数据的绘制，主要用于地图上的航线，路线的可视化+++++++++++++++++++++
	bar(3, "柱状图"), // 柱状图
	pie(4, "饼图"), // 饼图
	radar(5, "雷达图"), // 雷达图
	funnel(6, "漏斗图"), // 漏斗图
	gauge(7, "仪表盘"), // 仪表盘
	;
	
	private Integer value;
	
	private String text;
	
	private SeriesTypeEnum(Integer value, String text) {
		this.value = value;
		this.text = text;
	}

	/**
	 * 返回json格式的数据
	 * @return
	 */
	public static Collection<ValueTextBean> toJSON() {
		Collection<ValueTextBean> list = new ArrayList<>();
		list.add(new ValueTextBean(line.getValue(), line.getText()));
		list.add(new ValueTextBean(bar.getValue(), bar.getText()));
		/*for (SeriesTypeEnum e : values()) {
			list.add(new ValueTextBean(e.getValue(), e.name()));
		}*/
		return list;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
