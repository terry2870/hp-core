/*
 * 作者：黄平
 * 
 */
package com.hp.core.jsptags.tags;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.utils.ObjectUtil;
import com.hp.core.common.utils.SpringContextUtil;
import com.hp.core.jsptags.ognl.OgnlCache;


/**
 * 页面输出
 * 
 * @author Administrator
 * 
 */
public class WriteTag extends BaseTagSupport {

	private static final long serialVersionUID = 1L;
	
	static Logger log = LoggerFactory.getLogger(WriteTag.class);

	private String converter;
	private boolean transferred;
	private int add;

	public int doStartTag() throws JspException {
		super.doStartTag();
		Object tmp = null;
		String result = null;
		String objName = this.name;
		if (StringUtils.isEmpty(objName)) {
			objName = super.DEFAULT_ATTRIBUTE_NAME;
		}
		try {
			if ("contextPath".equalsIgnoreCase(objName)) {
				result = this.request.getContextPath();
			} else {
				if (!StringUtils.isEmpty(this.property)) {
					objName = objName + "." + this.property;
				}
				tmp = OgnlCache.getValue(objName, getDataFromRequest(this.request));
				if (tmp == null || StringUtils.isEmpty(String.valueOf(tmp))) {
					result = "";
				} else {
					result = tmp.toString();
				}
				if (!StringUtils.isEmpty(this.converter)) {
					String[] arr = this.converter.split("[.]");
					if (arr.length != 2) {
						log.error("converter转换的格式不正确！");
					} else {
						result = (String) ObjectUtil.executeJavaMethod(SpringContextUtil.getBean(arr[0]), arr[1], new Class[] { String.class }, new String[] { result });
					}
				}
				if (add != 0 && ((String) result).matches("\\d+")) {
					int temp = Integer.parseInt(result);
					temp = temp + add;
					result = String.valueOf(temp);
				}
				if (StringUtils.isEmpty(result) && !StringUtils.isEmpty(this.defaultValue)) {
					result = this.defaultValue;
				}
				if (transferred) {
					result = StringEscapeUtils.escapeHtml4((String) result);
				}
			}
			this.out.print(result);
		} catch (Exception e) {
			log.error("页面输出出错！", e);
		}
		return EVAL_BODY_INCLUDE;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public void setTransferred(boolean transferred) {
		this.transferred = transferred;
	}

}
