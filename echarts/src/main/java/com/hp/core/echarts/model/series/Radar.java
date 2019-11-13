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
public class Radar extends Series<Radar> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	public Radar() {
		setType(SeriesTypeEnum.radar);
	}
	
	public Radar(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.radar, name, data);
	}
	
	public Radar(String name, Object... values) {
		super(SeriesTypeEnum.radar, name, values);
	}
}
