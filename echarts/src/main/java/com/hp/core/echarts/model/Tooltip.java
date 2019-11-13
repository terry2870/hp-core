/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.TooltipTriggerEnum;
import com.hp.core.echarts.enums.TooltipTriggerOnEnum;
import com.hp.core.echarts.model.axis.AxisPointer;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Tooltip extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3713653005868828268L;
	
	/**
	 * 是否显示提示框组件
	 */
	private Boolean show;
	
	/**
	 * 触发类型
	 */
	private TooltipTriggerEnum trigger;
	
	/**
	 * 坐标轴指示器配置项
	 */
	private AxisPointer axisPointer;
	
	/**
	 * 提示框触发的条件
	 */
	private TooltipTriggerOnEnum triggerOn;
	
	/**
	 * 浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置
	 */
	private Integer showDelay;
	
	/**
	 * 浮层隐藏的延迟，单位为 ms
	 * 默认值100
	 */
	private Integer hideDelay;
	
	/**
	 * 鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true
	 */
	private Boolean enterable;
	
	/**
	 * 提示框浮层的文本样式
	 */
	private TextStyle textStyle;

	/**
	 * 提示框浮层的位置，默认不设置时位置会跟随鼠标的位置
	 */
	private Object position;
	
	/**
	 * 提示框浮层内容格式器，支持字符串模板和回调函数两种形式
	 */
	private Object formatter;
	
	/**
	 * 提示框浮层的背景颜色。
	 */
	private String backgroundColor;
	
	/**
	 * 提示框浮层的边框颜色。
	 */
	private String borderColor;
	
	/**
	 * 提示框浮层的边框宽。
	 */
	private Integer borderWidth;
	
	/**
	 * 提示框浮层内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
	 */
	private Object padding;
	
	public Tooltip() {
		this.trigger = TooltipTriggerEnum.axis;
	}

	public Tooltip(TooltipTriggerEnum trigger) {
		this.trigger = trigger;
	}

	public Object getPosition() {
		return position;
	}

	public Tooltip setPosition(Object position) {
		this.position = position;
		return this;
	}

	public Object getFormatter() {
		return formatter;
	}

	public Tooltip setFormatter(Object formatter) {
		this.formatter = formatter;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public Tooltip setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public Tooltip setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	public Integer getBorderWidth() {
		return borderWidth;
	}

	public Tooltip setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}

	public Object getPadding() {
		return padding;
	}

	public Tooltip setPadding(Object padding) {
		this.padding = padding;
		return this;
	}

	public Boolean getShow() {
		return show;
	}

	public Tooltip setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public TooltipTriggerEnum getTrigger() {
		return trigger;
	}

	public Tooltip setTrigger(TooltipTriggerEnum trigger) {
		this.trigger = trigger;
		return this;
	}

	public AxisPointer getAxisPointer() {
		return axisPointer;
	}

	public Tooltip setAxisPointer(AxisPointer axisPointer) {
		this.axisPointer = axisPointer;
		return this;
	}

	public TooltipTriggerOnEnum getTriggerOn() {
		return triggerOn;
	}

	public Tooltip setTriggerOn(TooltipTriggerOnEnum triggerOn) {
		this.triggerOn = triggerOn;
		return this;
	}

	public Integer getShowDelay() {
		return showDelay;
	}

	public Tooltip setShowDelay(Integer showDelay) {
		this.showDelay = showDelay;
		return this;
	}

	public Integer getHideDelay() {
		return hideDelay;
	}

	public Tooltip setHideDelay(Integer hideDelay) {
		this.hideDelay = hideDelay;
		return this;
	}

	public Boolean getEnterable() {
		return enterable;
	}

	public Tooltip setEnterable(Boolean enterable) {
		this.enterable = enterable;
		return this;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public Tooltip setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}
	
	
}
