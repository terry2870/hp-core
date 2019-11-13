/**
 * 
 */
package com.hp.core.echarts.model.series;

import java.util.Collection;

import com.hp.core.echarts.enums.SeriesTypeEnum;
import com.hp.core.echarts.model.AbstractData;
import com.hp.core.echarts.model.Label;
import com.hp.core.echarts.model.Tooltip;

/**
 * @author huangping
 * 2018年10月9日
 */
@SuppressWarnings("unchecked")
public abstract class Series<T> extends AbstractData<SeriesData, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3714233405709751004L;

	/**
	 * 图表类型
	 */
	private SeriesTypeEnum type;
	
	/**
	 * 组件 ID
	 */
	private String id;
	
	/**
	 * 系列名称，用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列
	 */
	private String name;
	
	/**
	 * 系列中的数据内容数组
	 * 继承父类
	 */
	//private Collection<SeriesData> data;
	
	/**
	 * 图表标注
	 */
	private MarkPoint markPoint;
	
	/**
	 * 图表标线
	 */
	private MarkLine markLine;
	
	/**
	 * 图表标域，常用于标记图表中某个范围的数据，例如标出某段时间投放了广告
	 */
	private MarkArea markArea;
	
	/**
	 * 柱状图所有图形的 zlevel 值
	 * 默认值0
	 */
	private Integer zlevel;
	
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
	
	/**
	 * 本系列特定的 tooltip 设定
	 */
	private Tooltip tooltip;
	
	/**
	 * 使用的 x 轴的 index，在单个图表实例中存在多个 x 轴的时候有用
	 */
	private Integer xAxisIndex;
	
	/**
	 * 使用的 y 轴的 index，在单个图表实例中存在多个 y轴的时候有用。
	 */
	private Integer yAxisIndex;
	
	private Label label;
	
	/**
	 * 不为空，则堆积起来
	 */
	private String stack;
	
	public Series() {}
		
	public Series(SeriesTypeEnum type) {
		this.type = type;
	}
	
	public Series(SeriesTypeEnum type, String name) {
		this(type);
		this.name = name;
	}
	
	public Series(SeriesTypeEnum type, String name, Collection<SeriesData> data) {
		this(type, name);
		super.setData(data);
	}
	
	public Series(SeriesTypeEnum type, String name, Object... values) {
		this(type, name);
		super.addData2(values);
	}
	

	public SeriesTypeEnum getType() {
		return type;
	}

	public T setType(SeriesTypeEnum type) {
		this.type = type;
		return (T) this;
	}

	public String getId() {
		return id;
	}

	public T setId(String id) {
		this.id = id;
		return (T) this;
	}

	public String getName() {
		return name;
	}

	public T setName(String name) {
		this.name = name;
		return (T) this;
	}
	
	public MarkPoint getMarkPoint() {
		return markPoint;
	}

	public T setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
		return (T) this;
	}

	public MarkLine getMarkLine() {
		return markLine;
	}

	public T setMarkLine(MarkLine markLine) {
		this.markLine = markLine;
		return (T) this;
	}

	public MarkArea getMarkArea() {
		return markArea;
	}

	public T setMarkArea(MarkArea markArea) {
		this.markArea = markArea;
		return (T) this;
	}

	public Integer getZlevel() {
		return zlevel;
	}

	public T setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
		return (T) this;
	}

	public Boolean getAnimation() {
		return animation;
	}

	public T setAnimation(Boolean animation) {
		this.animation = animation;
		return (T) this;
	}

	public Integer getAnimationThreshold() {
		return animationThreshold;
	}

	public T setAnimationThreshold(Integer animationThreshold) {
		this.animationThreshold = animationThreshold;
		return (T) this;
	}

	public Integer getAnimationDuration() {
		return animationDuration;
	}

	public T setAnimationDuration(Integer animationDuration) {
		this.animationDuration = animationDuration;
		return (T) this;
	}

	public String getAnimationEasing() {
		return animationEasing;
	}

	public T setAnimationEasing(String animationEasing) {
		this.animationEasing = animationEasing;
		return (T) this;
	}

	public Object getAnimationDelay() {
		return animationDelay;
	}

	public T setAnimationDelay(Object animationDelay) {
		this.animationDelay = animationDelay;
		return (T) this;
	}

	public Object getAnimationDurationUpdate() {
		return animationDurationUpdate;
	}

	public T setAnimationDurationUpdate(Object animationDurationUpdate) {
		this.animationDurationUpdate = animationDurationUpdate;
		return (T) this;
	}

	public String getAnimationEasingUpdate() {
		return animationEasingUpdate;
	}

	public T setAnimationEasingUpdate(String animationEasingUpdate) {
		this.animationEasingUpdate = animationEasingUpdate;
		return (T) this;
	}

	public Object getAnimationDelayUpdate() {
		return animationDelayUpdate;
	}

	public T setAnimationDelayUpdate(Object animationDelayUpdate) {
		this.animationDelayUpdate = animationDelayUpdate;
		return (T) this;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public T setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
		return (T) this;
	}

	public Integer getxAxisIndex() {
		return xAxisIndex;
	}

	public T setxAxisIndex(Integer xAxisIndex) {
		this.xAxisIndex = xAxisIndex;
		return (T) this;
	}

	public Integer getyAxisIndex() {
		return yAxisIndex;
	}

	public T setyAxisIndex(Integer yAxisIndex) {
		this.yAxisIndex = yAxisIndex;
		return (T) this;
	}
	
	@Override
	public SeriesData convert(Object v) {
		return new SeriesData(v);
	}

	public Label getLabel() {
		return label;
	}

	public T setLabel(Label label) {
		this.label = label;
		return (T) this;
	}

	public String getStack() {
		return stack;
	}

	public T setStack(String stack) {
		this.stack = stack;
		return (T) this;
	}
}
