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
public class Funnel extends Series<Funnel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	public Funnel() {
		setType(SeriesTypeEnum.funnel);
	}
	
	public Funnel(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.funnel, name, data);
	}
	
	public Funnel(String name, Object... values) {
		super(SeriesTypeEnum.funnel, name, values);
	}
}
