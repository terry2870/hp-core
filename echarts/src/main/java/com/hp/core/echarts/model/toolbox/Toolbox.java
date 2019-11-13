/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.ToolboxOrientEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Toolbox extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7303019030620803735L;

	/**
	 * 组件 ID
	 */
	private String id;
	
	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 工具栏 icon 的布局朝向
	 * 默认horizontal
	 */
	private ToolboxOrientEnum orient;
	
	/**
	 * 工具栏 icon 的大小
	 * 默认值15
	 */
	private Integer itemSize;
	
	/**
	 * 工具栏 icon 每项之间的间隔。横向布局时为水平间隔，纵向布局时为纵向间隔。
	 * 默认值10
	 */
	private Integer itemGap;
	
	/**
	 * 是否在鼠标 hover 的时候显示每个工具 icon 的标题。
	 * 默认值true
	 */
	private Boolean showTitle;
	
	/**
	 * 各工具配置项
	 */
	private Feature feature = new Feature();

	public String getId() {
		return id;
	}

	public Toolbox setId(String id) {
		this.id = id;
		return this;
	}

	public Boolean getShow() {
		return show;
	}

	public Toolbox setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public ToolboxOrientEnum getOrient() {
		return orient;
	}

	public Toolbox setOrient(ToolboxOrientEnum orient) {
		this.orient = orient;
		return this;
	}

	public Integer getItemSize() {
		return itemSize;
	}

	public Toolbox setItemSize(Integer itemSize) {
		this.itemSize = itemSize;
		return this;
	}

	public Integer getItemGap() {
		return itemGap;
	}

	public Toolbox setItemGap(Integer itemGap) {
		this.itemGap = itemGap;
		return this;
	}

	public Boolean getShowTitle() {
		return showTitle;
	}

	public Toolbox setShowTitle(Boolean showTitle) {
		this.showTitle = showTitle;
		return this;
	}

	public Feature getFeature() {
		return feature;
	}

	public Toolbox setFeature(Feature feature) {
		this.feature = feature;
		return this;
	}
}
