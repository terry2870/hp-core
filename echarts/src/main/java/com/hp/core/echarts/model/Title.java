/**
 * 
 */
package com.hp.core.echarts.model;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.echarts.enums.LinkTargetEnum;

/**
 * @author huangping
 * 2018年10月9日
 */
public class Title extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6558961976107507627L;

	/**
	 * 组件 ID。默认不指定。指定则可用于在 option 或者 API 中引用组件
	 */
	private String id;
	
	/**
	 * 是否显示标题组件
	 * 默认值true
	 */
	private Boolean show;
	
	/**
	 * 主标题文本，支持使用 \n 换行
	 */
	private String text;
	
	/**
	 * 主标题文本超链接
	 */
	private String link;
	
	/**
	 * 指定窗口打开主标题超链接。
	 * 默认值blank
	 */
	private LinkTargetEnum target;
	
	/**
	 * 主标题样式
	 */
	private TextStyle textStyle;
	
	/**
	 * 副标题
	 */
	private String subtext;
	
	/**
	 * 副标题样式
	 */
	private TextStyle subtextStyle;
	
	public Title(String text) {
		this.text = text;
	}
	
	public Title(String text, String subtext) {
		this(text);
		this.subtext = subtext;
	}
	
	public Title(String text, String subtext, String link) {
		this(text, subtext);
		this.link = link;
	}
	
	public Title(String text, String subtext, String link, TextStyle textStyle, TextStyle subtextStyle) {
		this(text, subtext, link);
		this.textStyle = textStyle;
		this.subtextStyle = subtextStyle;
	}

	public String getId() {
		return id;
	}

	public Title setId(String id) {
		this.id = id;
		return this;
	}

	public Boolean getShow() {
		return show;
	}

	public Title setShow(Boolean show) {
		this.show = show;
		return this;
	}

	public String getText() {
		return text;
	}

	public Title setText(String text) {
		this.text = text;
		return this;
	}

	public String getLink() {
		return link;
	}

	public Title setLink(String link) {
		this.link = link;
		return this;
	}

	public LinkTargetEnum getTarget() {
		return target;
	}

	public Title setTarget(LinkTargetEnum target) {
		this.target = target;
		return this;
	}

	public TextStyle getTextStyle() {
		return textStyle;
	}

	public Title setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
		return this;
	}

	public String getSubtext() {
		return subtext;
	}

	public Title setSubtext(String subtext) {
		this.subtext = subtext;
		return this;
	}

	public TextStyle getSubtextStyle() {
		return subtextStyle;
	}

	public Title setSubtextStyle(TextStyle subtextStyle) {
		this.subtextStyle = subtextStyle;
		return this;
	}
}
