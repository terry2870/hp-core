/**
 * 
 */
package com.hp.core.echarts.model.axis;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.AxisPointerTypeEnum;
import com.hp.core.echarts.model.Label;

/**
 * @author huangping
 * 2018年10月9日
 */
public class AxisPointer extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7246142745373282238L;

	/**
	 * 是否显示
	 * 默认值false
	 */
	private Boolean show;
	
	/**
	 * 指示器类型
	 * 默认line
	 */
	private AxisPointerTypeEnum type;
	
	/**
	 * 坐标轴指示器是否自动吸附到点上。默认自动判断
	 */
	private Boolean snap;
	
	/**
	 * 坐标轴指示器的 z 值。控制图形的前后顺序。z值小的图形会被z值大的图形覆盖。
	 */
	private Integer z;
	
	/**
	 * 坐标轴指示器的文本标签
	 */
	private Label label;
	
	/**
	 * 线的样式
	 */
	private LineStyle lineStyle;
	
	public AxisPointer() {}
	
	public AxisPointer(AxisPointerTypeEnum type) {
		this.type = type;
	}

	public Boolean getShow() {
		return show;
	}

	public AxisPointer setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public AxisPointerTypeEnum getType() {
		return type;
	}

	public AxisPointer setType(AxisPointerTypeEnum type) {
		this.type = type;
		return this;
	}

	public Boolean getSnap() {
		return snap;
	}

	public AxisPointer setSnap(Boolean snap) {
		this.snap = snap;
		return this;
	}

	public Integer getZ() {
		return z;
	}

	public AxisPointer setZ(Integer z) {
		this.z = z;
		return this;
	}

	public Label getLabel() {
		return label;
	}

	public AxisPointer setLabel(Label label) {
		this.label = label;
		return this;
	}

	public LineStyle getLineStyle() {
		return lineStyle;
	}

	public AxisPointer setLineStyle(LineStyle lineStyle) {
		this.lineStyle = lineStyle;
		return this;
	}
}
