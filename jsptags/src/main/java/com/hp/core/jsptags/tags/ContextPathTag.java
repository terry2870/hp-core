/*
 * 作者：黄平
 * 
 */
package com.hp.core.jsptags.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContextPathTag extends BaseTagSupport {

	private static final long serialVersionUID = 1L;
	static Logger log = LoggerFactory.getLogger(ContextPathTag.class);

	public int doStartTag() throws JspException {
		super.doStartTag();
		try {
			this.out.print(this.request.getContextPath());
		} catch (IOException e) {
			log.error("", e);
		}
		return EVAL_BODY_INCLUDE;
	}
}
