/**
 * 
 */
package com.hp.core.echarts.model.toolbox;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.ImageTypeEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class SaveAsImage extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852902683370376128L;

	/**
	 * 图片类型
	 * 默认值png
	 */
	private ImageTypeEnum type;
	
	/**
	 * 保存的文件名称
	 * 默认使用 title.text 作为名称
	 */
	private String name;
	
	/**
	 * 保存的图片背景色
	 * 默认使用 backgroundColor，如果backgroundColor不存在的话会取白色
	 */
	private String backgroundColor;
	
	/**
	 * 保存为图片时忽略的组件列表
	 * 默认忽略工具栏 ['toolbox'] 
	 */
	private String[] excludeComponents;
	
	/**
	 * 是否显示
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 显示文字
	 * 默认值"保存为图片"
	 */
	private String title;
	
	/**
	 * 可以通过 'image://url' 设置为图片，其中 URL 为图片的链接，或者 dataURI
	 */
	private String icon;
	
	public SaveAsImage() {}

	public SaveAsImage(Boolean show) {
		this.show = show;
	}

	public ImageTypeEnum getType() {
		return type;
	}

	public SaveAsImage setType(ImageTypeEnum type) {
		this.type = type;
		return this;
	}

	public String getName() {
		return name;
	}

	public SaveAsImage setName(String name) {
		this.name = name;
		return this;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public SaveAsImage setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	public String[] getExcludeComponents() {
		return excludeComponents;
	}

	public SaveAsImage setExcludeComponents(String[] excludeComponents) {
		this.excludeComponents = excludeComponents;
		return this;
	}

	public Boolean getShow() {
		return show;
	}

	public SaveAsImage setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public SaveAsImage setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getIcon() {
		return icon;
	}

	public SaveAsImage setIcon(String icon) {
		this.icon = icon;
		return this;
	}
}
