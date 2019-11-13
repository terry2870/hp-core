/**
 * 
 */
package com.hp.core.echarts.model.series;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.model.Label;
import com.hp.core.echarts.model.Tooltip;

/**
 * @author huangping
 * 2018年10月9日
 */
public class SeriesData extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2839289744671740399L;

	/**
	 * 数据项名称
	 */
	private String name;
	
	/**
	 * 单个数据项的数值
	 */
	private Object value;
	
	/**
	 * 单个柱条文本的样式设置
	 */
	private Label label;
	
	/**
	 * 传递额外的参数
	 */
	private Object extraData;
	
	/**
	 * 本系列每个数据项中特定的 tooltip 设定
	 */
	private Tooltip tooltip;

	public SeriesData() {}
	
	public SeriesData(Object value) {
		this.value = value;
	}
	
	public SeriesData(String name, Object value) {
		this(value);
		this.name = name;
	}
	
	public SeriesData(String name, Object value, Label label) {
		this(name, value);
		this.label = label;
	}
	
	public SeriesData(String name, Object value, Label label, Object extraData) {
		this(name, value, label);
		this.extraData = extraData;
	}
	
	public SeriesData(String name, Object value, Label label, Object extraData, Tooltip tooltip) {
		this(name, value, label, extraData);
		this.tooltip = tooltip;
	}

	public String getName() {
		return name;
	}

	public SeriesData setName(String name) {
		this.name = name;
		return this;
	}

	public Object getValue() {
		return value;
	}

	public SeriesData setValue(Object value) {
		this.value = value;
		return this;
	}

	public Label getLabel() {
		return label;
	}

	public SeriesData setLabel(Label label) {
		this.label = label;
		return this;
	}

	public Object getExtraData() {
		return extraData;
	}

	public SeriesData setExtraData(Object extraData) {
		this.extraData = extraData;
		return this;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public SeriesData setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
		return this;
	}
}
