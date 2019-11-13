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
public class Gauge extends Series<Gauge> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	public Gauge() {
		setType(SeriesTypeEnum.gauge);
	}
	
	public Gauge(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.gauge, name, data);
	}
	
	public Gauge(String name, Object... values) {
		super(SeriesTypeEnum.gauge, name, values);
	}
}
