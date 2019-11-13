/**
 * 
 */
package com.hp.core.echarts.model.series;

import java.util.Collection;

import com.hp.core.echarts.enums.SamplingEnum;
import com.hp.core.echarts.enums.SeriesTypeEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Line extends Series<Line> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4381796617763933800L;

	/**
	 * 是否平滑曲线显示
	 * 如果是 boolean 类型，则表示是否开启平滑处理。如果是 number 类型（取值范围 0 到 1），表示平滑程度，越小表示越接近折线段，反之则反。设为 true 时相当于设为 0.5。
	 */
	private Object smooth;
	
	/**
	 * 折线图在数据量远大于像素点时候的降采样策略，开启后可以有效的优化图表的绘制效率，默认关闭
	 */
	private SamplingEnum sampling;
	
	/**
	 * 堆积的背景
	 */
	private AreaStyle areaStyle;
	
	public Line() {
		setType(SeriesTypeEnum.line);
	}
	
	public Line(String name, Collection<SeriesData> data) {
		super(SeriesTypeEnum.line, name, data);
	}
	
	public Line(String name, Object... values) {
		super(SeriesTypeEnum.line, name, values);
	}

	public Object getSmooth() {
		return smooth;
	}

	public Line setSmooth(Object smooth) {
		this.smooth = smooth;
		return this;
	}

	public SamplingEnum getSampling() {
		return sampling;
	}

	public Line setSampling(SamplingEnum sampling) {
		this.sampling = sampling;
		return this;
	}

	public AreaStyle getAreaStyle() {
		return areaStyle;
	}

	public void setAreaStyle(AreaStyle areaStyle) {
		this.areaStyle = areaStyle;
	}
}
