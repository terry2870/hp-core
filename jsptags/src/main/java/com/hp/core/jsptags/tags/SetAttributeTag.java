/*
 * 作者：黄平
 * 
 */
package com.hp.core.jsptags.tags;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SetAttributeTag extends BaseTagSupport {

	private static final long serialVersionUID = 1L;
	
	static Logger log = LoggerFactory.getLogger(SetAttributeTag.class);

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		try {
			this.request.setAttribute(this.beanName, this.getValuesFromParam());
		} catch (Exception e) {
			log.error("", e);
		}
		return EVAL_BODY_INCLUDE;
	}
}
