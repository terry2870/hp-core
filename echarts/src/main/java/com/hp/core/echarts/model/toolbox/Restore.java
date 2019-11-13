/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Restore extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6040233034486914264L;

	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 显示文字
	 * 默认值“还原”
	 */
	private String title;
	
	/**
	 * 可以通过 'image://url' 设置为图片，其中 URL 为图片的链接，或者 dataURI
	 */
	private String icon;

	public Restore() {}
	
	public Restore(Boolean show) {
		this.show = show;
	}

	public Boolean getShow() {
		return show;
	}

	public Restore setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Restore setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public Restore setIcon(String icon) {
		this.icon = icon;
		return this;
	}
}
