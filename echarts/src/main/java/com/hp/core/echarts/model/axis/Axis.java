/**
 * 
 */
package com.hp.core.echarts.model.axis;

import java.util.Collection;

import com.hp.core.echarts.enums.AxisTypeEnum;
import com.hp.core.echarts.enums.PositionEnum;
import com.hp.core.echarts.model.AbstractData;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Axis extends AbstractData<AxisData, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836426038726314445L;

	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 轴的位置
	 * 可选（top，bottom）
	 */
	private PositionEnum position;
	
	/**
	 * 坐标轴类型
	 */
	private AxisTypeEnum type;
	
	/**
	 * 坐标轴名称
	 */
	private String name;
	
	/**
	 * 坐标轴刻度最小值
	 */
	private Object min;
	
	/**
	 * 坐标轴刻度最大值
	 */
	private Object max;
	
	/**
	 * 类目数据
	 * 继承父类
	 */
	//private Collection<AxisData> data;
	
	/**
	 * 
	 */
	private AxisPointer axisPointer;
	
	/**
	 * zlevel 值。
	 */
	private Integer zlevel;
	
	private Integer z;
	
	/**
	 * 坐标轴两边留白策略，类目轴和非类目轴的设置和表现不一样。
	 */
	private Object boundaryGap;

	public Axis() {}
	
	public Axis(AxisTypeEnum type) {
		this.type = type;
	}
	
	public Axis(Collection<AxisData> data) {
		super.setData(data);
	}
	
	public Axis(AxisTypeEnum type, Collection<AxisData> data) {
		this(type);
		super.setData(data);
	}
	
	public Axis(AxisTypeEnum type, Object... datas) {
		this(type);
		addData2(datas);
	}

	public Boolean getShow() {
		return show;
	}

	public Axis setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public Axis setPosition(PositionEnum position) {
		this.position = position;
		return this;
	}

	public AxisTypeEnum getType() {
		return type;
	}

	public Axis setType(AxisTypeEnum type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public Axis setName(String name) {
		this.name = name;
		return this;
	}

	public Object getMin() {
		return min;
	}

	public Axis setMin(Object min) {
		this.min = min;
		return this;
	}

	public Object getMax() {
		return max;
	}

	public Axis setMax(Object max) {
		this.max = max;
		return this;
	}

	public AxisPointer getAxisPointer() {
		return axisPointer;
	}

	public Axis setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
		return this;
	}

	public Integer getZlevel() {
		return zlevel;
	}

	public Axis setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
		return this;
	}

	public Integer getZ() {
		return z;
	}

	public Axis setZ(Integer z) {
		this.z = z;
		return this;
	}

	public Object getBoundaryGap() {
		return boundaryGap;
	}

	public void setBoundaryGap(Object boundaryGap) {
		this.boundaryGap = boundaryGap;
	}

	@Override
	public AxisData convert(Object v) {
		return new AxisData(v);
	}
}
