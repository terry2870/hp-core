/**
 * 
 */
package com.hp.core.common.beans;
/**
 * @author huangping
 * Apr 28, 2019
 */
public class ValueTextBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6679545513489537888L;

	
	private Object value;
	
	private String text;
	
	/**
	 * 额外参数
	 */
	private Object extraParams;
	
	public ValueTextBean() {}

	public ValueTextBean(Object value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public ValueTextBean(Object value, String text, Object extraParams) {
		this(value, text);
		this.extraParams = extraParams;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getExtraParams() {
		return extraParams;
	}

	public void setExtraParams(Object extraParams) {
		this.extraParams = extraParams;
	}
}
