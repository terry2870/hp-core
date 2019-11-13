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
public class Bar extends Series<Bar> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	public Bar() {
		setType(SeriesTypeEnum.bar);
	}
	
	public Bar(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.bar, name, data);
	}
	
	public Bar(String name, Object... values) {
		super(SeriesTypeEnum.bar, name, values);
	}
}
