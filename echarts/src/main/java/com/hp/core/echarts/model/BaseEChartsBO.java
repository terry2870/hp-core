/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
@SuppressWarnings("unchecked")
public abstract class BaseEChartsBO<T> extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8282643880714845601L;
	/**
     * 是否显示
     */
    private Boolean show;
    /**
     * 水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
     *
     * @see com.github.abel533.echarts.code.X
     */
    private Object x;
    /**
     * 垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
     *
     * @see com.github.abel533.echarts.code.Y
     */
    private Object y;
    /**
     * 标题背景颜色，默认透明
     */
    private String backgroundColor;
    /**
     * 标题边框颜色
     */
    private String borderColor;
    /**
     * borderWidth
     */
    private Integer borderWidth;
    /**
     * 标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距，同css，见下图
     */
    private Object padding;
    /**
     * 主副标题纵向间隔，单位px，默认为10
     */
    private Integer itemGap;
    /**
     * 一级层叠控制
     */
    private Integer zlevel;
    /**
     * 二级层叠控制
     */
    private Integer z;

    /**
     * treemap 组件离容器左侧的距离
     */
    private Object left;
    /**
     * treemap 组件离容器上侧的距离
     */
    private Object top;
    /**
     * treemap 组件离容器右侧的距离
     */
    private Object right;
    /**
     * treemap 组件离容器下侧的距离
     */
    private Object bottom;
    /**
     * treemap 组件的宽度
     */
    private Object width;
    /**
     * treemap 组件的高度
     */
    private Object height;
    /**
     * 图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果
     */
    private Integer shadowBlur;
    /**
     * 阴影颜色
     */
    private String shadowColor;
    /**
     * 阴影水平方向上的偏移距离
     */
    private Integer shadowOffsetX;
    /**
     * 阴影垂直方向上的偏移距离
     */
    private Integer shadowOffsetY;
    
    
	public Boolean getShow() {
		return show;
	}
	
	public T setShow(Boolean show) {
		this.show = show;
		return (T) this;
	}
	public Object getX() {
		return x;
	}
	public T setX(Object x) {
		this.x = x;
		return (T) this;
	}
	public Object getY() {
		return y;
	}
	public T setY(Object y) {
		this.y = y;
		return (T) this;
	}
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public T setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return (T) this;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public T setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return (T) this;
	}
	public Integer getBorderWidth() {
		return borderWidth;
	}
	public T setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
		return (T) this;
	}
	public Object getPadding() {
		return padding;
	}
	public T setPadding(Object padding) {
		this.padding = padding;
		return (T) this;
	}
	public Integer getItemGap() {
		return itemGap;
	}
	public T setItemGap(Integer itemGap) {
		this.itemGap = itemGap;
		return (T) this;
	}
	public Integer getZlevel() {
		return zlevel;
	}
	public T setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
		return (T) this;
	}
	public Integer getZ() {
		return z;
	}
	public T setZ(Integer z) {
		this.z = z;
		return (T) this;
	}
	public Object getLeft() {
		return left;
	}
	public T setLeft(Object left) {
		this.left = left;
		return (T) this;
	}
	public Object getTop() {
		return top;
	}
	public T setTop(Object top) {
		this.top = top;
		return (T) this;
	}
	public Object getRight() {
		return right;
	}
	public T setRight(Object right) {
		this.right = right;
		return (T) this;
	}
	public Object getBottom() {
		return bottom;
	}
	public T setBottom(Object bottom) {
		this.bottom = bottom;
		return (T) this;
	}
	public Object getWidth() {
		return width;
	}
	public T setWidth(Object width) {
		this.width = width;
		return (T) this;
	}
	public Object getHeight() {
		return height;
	}
	public T setHeight(Object height) {
		this.height = height;
		return (T) this;
	}
	public Integer getShadowBlur() {
		return shadowBlur;
	}
	public T setShadowBlur(Integer shadowBlur) {
		this.shadowBlur = shadowBlur;
		return (T) this;
	}
	public String getShadowColor() {
		return shadowColor;
	}
	public T setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
		return (T) this;
	}
	public Integer getShadowOffsetX() {
		return shadowOffsetX;
	}
	public T setShadowOffsetX(Integer shadowOffsetX) {
		this.shadowOffsetX = shadowOffsetX;
		return (T) this;
	}
	public Integer getShadowOffsetY() {
		return shadowOffsetY;
	}
	public T setShadowOffsetY(Integer shadowOffsetY) {
		this.shadowOffsetY = shadowOffsetY;
		return (T) this;
	}
}
