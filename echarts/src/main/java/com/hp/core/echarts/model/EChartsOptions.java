/**
 * 
 */
package com.hp.core.echarts.model;

import java.util.Collection;

import com.alibaba.fastjson.JSON;
import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.model.axis.Axis;
import com.hp.core.echarts.model.series.Series;
import com.hp.core.echarts.model.toolbox.Toolbox;

/**
 * echart的主返回对象
 * @author huangping
 * 2018年10月9日
 */
public class EChartsOptions extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2673684401231134758L;

	/**
	 * 标题
	 */
	private Title title;
	
	/**
	 * 图例
	 */
	private Legend legend;
	
	/**
	 * 直角坐标系内绘图网格，单个 grid 内最多可以放置上下两个 X 轴，左右两个 Y 轴
	 */
	private Grid grid;
	
	/**
	 * 直角坐标系 grid 中的 x 轴
	 */
	private Axis xAxis;
	
	/**
	 * 直角坐标系 grid 中的 y 轴
	 */
	private Axis yAxis;
	
	/**
	 * 提示框组件
	 */
	private Tooltip tooltip;
	
	/**
	 * 工具栏
	 */
	private Toolbox toolbox;
	
	/**
	 * 系列列表
	 */
	private Collection<Series<?>> series;
	
	/**
	 * 全局的字体样式
	 */
	private TextStyle textStyle;
	
	/**
	 * 是否开启动画
	 * 默认true
	 */
	private Boolean animation;
	
	/**
	 * 是否开启动画的阈值，当单个系列显示的图形数量大于这个阈值时会关闭动画
	 * 默认2000
	 */
	private Integer animationThreshold;
	
	/**
	 * 初始动画的时长，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的初始动画效果
	 * 默认值1000
	 */
	private Integer animationDuration;
	
	/**
	 * 初始动画的缓动效果
	 * 默认值cubicOut
	 */
	private String animationEasing;
	
	/**
	 * 始动画的延迟，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的初始动画效果
	 * 默认值0
	 */
	private Object animationDelay;
	
	/**
	 * 数据更新动画的时长，支持回调函数
	 * 默认值300
	 */
	private Object animationDurationUpdate;
	
	/**
	 * 数据更新动画的缓动效果
	 * 默认值cubicOut
	 */
	private String animationEasingUpdate;
	
	/**
	 * 数据更新动画的延迟，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的更新动画效果
	 * 默认值0
	 */
	private Object animationDelayUpdate;

	public Title getTitle() {
		return title;
	}

	public EChartsOptions setTitle(Title title) {
		this.title = title;
		return this;
	}

	public Legend getLegend() {
		return legend;
	}

	public EChartsOptions setLegend(Legend legend) {
		this.legend = legend;
		return this;
	}

	public Grid getGrid() {
		return grid;
	}

	public EChartsOptions setGrid(Grid grid) {
		this.grid = grid;
		return this;
	}

	public Axis getxAxis() {
		return xAxis;
	}

	public EChartsOptions setxAxis(Axis xAxis) {
		this.xAxis = xAxis;
		return this;
	}

	public Axis getyAxis() {
		return yAxis;
	}

	public EChartsOptions setyAxis(Axis yAxis) {
		this.yAxis = yAxis;
		return this;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public EChartsOptions setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public Toolbox getToolbox() {
		return toolbox;
	}

	public EChartsOptions setToolbox(Toolbox toolbox) {
		this.toolbox = toolbox;
		return this;
	}

	public Collection<Series<?>> getSeries() {
		return series;
	}

	public EChartsOptions setSeries(Collection<Series<?>> series) {
		this.series = series;
		return this;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public EChartsOptions setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}

	public Boolean getAnimation() {
		return animation;
	}

	public EChartsOptions setAnimation(Boolean animation) {
		this.animation = animation;
		return this;
	}

	public Integer getAnimationThreshold() {
		return animationThreshold;
	}

	public EChartsOptions setAnimationThreshold(Integer animationThreshold) {
		this.animationThreshold = animationThreshold;
		return this;
	}

	public Integer getAnimationDuration() {
		return animationDuration;
	}

	public EChartsOptions setAnimationDuration(Integer animationDuration) {
		this.animationDuration = animationDuration;
		return this;
	}

	public String getAnimationEasing() {
		return animationEasing;
	}

	public EChartsOptions setAnimationEasing(String animationEasing) {
		this.animationEasing = animationEasing;
		return this;
	}

	public Object getAnimationDelay() {
		return animationDelay;
	}

	public EChartsOptions setAnimationDelay(Object animationDelay) {
		this.animationDelay = animationDelay;
		return this;
	}

	public Object getAnimationDurationUpdate() {
		return animationDurationUpdate;
	}

	public EChartsOptions setAnimationDurationUpdate(Object animationDurationUpdate) {
		this.animationDurationUpdate = animationDurationUpdate;
		return this;
	}

	public String getAnimationEasingUpdate() {
		return animationEasingUpdate;
	}

	public EChartsOptions setAnimationEasingUpdate(String animationEasingUpdate) {
		this.animationEasingUpdate = animationEasingUpdate;
		return this;
	}

	public Object getAnimationDelayUpdate() {
		return animationDelayUpdate;
	}

	public EChartsOptions setAnimationDelayUpdate(Object animationDelayUpdate) {
		this.animationDelayUpdate = animationDelayUpdate;
		return this;
	}
	
	public String toString(boolean format) {
		return JSON.toJSONString(this, format);
	}
}
