/**
 * 
 */
package com.hp.core.jsptags.tags;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.jsptags.ognl.OgnlCache;

/**
 * @author huangping
 * 2017年8月4日 下午11:59:58
 */
public class IfTag extends BaseTagSupport {
	
	private static final long serialVersionUID = -9013915511433124222L;
	private String test;
	
	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		if (StringUtils.isEmpty(test)) {
			log.warn("IfTag error. with test is empty");
			return SKIP_BODY;
		}

		Object value = OgnlCache.getValue(test, getDataFromRequest(this.request));
		if (Boolean.TRUE.equals(value)) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}
	
	
	public void setTest(String test) {
		this.test = test;
	}
	
	
}
