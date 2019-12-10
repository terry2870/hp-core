/*
 * 作者：黄平
 * 
 */
package com.hp.core.jsptags.tags;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.jsptags.ognl.OgnlCache;


public class BaseTagSupport extends TagSupport {

	private static final long serialVersionUID = 1L;
	
	protected final String DEFAULT_ATTRIBUTE_NAME = "defaultAttributeName";
	protected final String DEFAULT_INDEX_ATTRIBUTE_NAME = "defaultIndexAttributeName";
	protected final String DEFAULT_TOTAL_ATTRIBUTE_NAME = "defaultTotalAttributeName";
	
	static Logger log = LoggerFactory.getLogger(BaseTagSupport.class);

	protected String name;
	protected String label;
	protected String value;
	protected String size;
	protected String event;
	protected String className;
	protected String style;
	protected String beanName;
	protected String align;

	protected String checked;
	protected String selected;
	protected String disabled;
	protected String readonly;

	protected String labelClassName;
	protected String labelStyle;
	protected String labelEvent;
	protected String textClassName;
	protected String textStyle;
	protected String textEvent;
	protected String property;
	protected String src;
	protected String height;
	protected String width;

	protected String defaultValue;

	protected String xml;
	protected String pageSize;
	protected String formName;

	protected String checkBoxClassName;
	protected String checkBoxStyle;
	protected String checkBoxEvent;

	protected String radioClassName;
	protected String radioStyle;
	protected String radioEvent;

	protected String labelField;
	protected String valueField;
	protected String checkedField;
	protected String rowSize;
	protected String splitChar;

	protected String rootNodePid;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletConfig servletConfig;
	protected ServletContext servletContext;
	protected HttpSession session;
	protected JspWriter out;

	@Override
	public int doStartTag() throws JspException {
		this.request = (HttpServletRequest) this.pageContext.getRequest();
		this.response = (HttpServletResponse) this.pageContext.getResponse();
		this.servletConfig = this.pageContext.getServletConfig();
		this.servletContext = this.pageContext.getServletContext();
		this.session = this.pageContext.getSession();
		this.out = this.pageContext.getOut();
		return super.doStartTag();
	}

	/**
	 * 从request中取数据
	 * 优先级parameter>attribute>session
	 * @param request
	 * @return
	 */
	protected Map<String, Object> getDataFromRequest(HttpServletRequest request) {
		//先从session中，再取attribute，再取parameter
		Map<String, Object> map = new HashMap<>();
		
		//session
		Enumeration<String> enu = request.getSession().getAttributeNames();
		String key = null;
		while (enu.hasMoreElements()) {
			key = enu.nextElement();
			map.put(key, request.getSession().getAttribute(key));
		}
		
		//attribute
		enu = request.getAttributeNames();
		while (enu.hasMoreElements()) {
			key = enu.nextElement();
			map.put(key, request.getAttribute(key));
		}
		
		//parameter
		enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			key = enu.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}
	
	protected String getValuesFromParam() throws Exception {
		String result = null;
		if (this.value != null) {
			if (this.value.indexOf("{") == 0 && this.value.indexOf("}") > 0) {
				Object tmp = OgnlCache.getValue(this.value.substring(this.value.indexOf("{") + 1, this.value.indexOf("}")), getDataFromRequest(this.request));
				if (tmp != null && !StringUtils.isEmpty(String.valueOf(tmp))) {
					result = tmp.toString();
				}
			} else {
				result = this.value;
			}
		}
		if (result == null && this.defaultValue != null) {
			result = this.defaultValue;
		}
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public void setLabelClassName(String labelClassName) {
		this.labelClassName = labelClassName;
	}

	public void setLabelStyle(String labelStyle) {
		this.labelStyle = labelStyle;
	}

	public void setLabelEvent(String labelEvent) {
		this.labelEvent = labelEvent;
	}

	public void setTextStyle(String textStyle) {
		this.textStyle = textStyle;
	}

	public void setTextEvent(String textEvent) {
		this.textEvent = textEvent;
	}

	public void setTextClassName(String textClassName) {
		this.textClassName = textClassName;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setCheckBoxClassName(String checkBoxClassName) {
		this.checkBoxClassName = checkBoxClassName;
	}

	public void setCheckBoxStyle(String checkBoxStyle) {
		this.checkBoxStyle = checkBoxStyle;
	}

	public void setCheckBoxEvent(String checkBoxEvent) {
		this.checkBoxEvent = checkBoxEvent;
	}

	public void setLabelField(String labelField) {
		this.labelField = labelField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public void setRowSize(String rowSize) {
		this.rowSize = rowSize;
	}

	public void setRadioClassName(String radioClassName) {
		this.radioClassName = radioClassName;
	}

	public void setRadioStyle(String radioStyle) {
		this.radioStyle = radioStyle;
	}

	public void setRadioEvent(String radioEvent) {
		this.radioEvent = radioEvent;
	}

	public void setRootNodePid(String rootNodePid) {
		this.rootNodePid = rootNodePid;
	}

	public void setSplitChar(String splitChar) {
		this.splitChar = splitChar;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setCheckedField(String checkedField) {
		this.checkedField = checkedField;
	}


}
