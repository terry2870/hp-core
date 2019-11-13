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
public class Lines extends Series<Lines> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	public Lines() {
		setType(SeriesTypeEnum.lines);
	}
	
	public Lines(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.lines, name, data);
	}
	
	public Lines(String name, Object... values) {
		super(SeriesTypeEnum.lines, name, values);
	}
}
