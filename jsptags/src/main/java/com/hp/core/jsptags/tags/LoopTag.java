/*
 * 作者：黄平
 * 
 */
package com.hp.core.jsptags.tags;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;

/**
 * 循环标签
 * 
 * @author Administrator
 * 
 */
public class LoopTag extends BaseTagSupport {

	private static final long serialVersionUID = 1L;
	private int times = 0;
	private String index = DEFAULT_INDEX_ATTRIBUTE_NAME;
	private int indexNum;

	// Set方法设值
	public void setTimes(int times) {
		this.times = times;
	}

	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		indexNum = 0;
		if (StringUtils.isNotEmpty(this.index)) {
			request.setAttribute(this.index, this.indexNum);
		}
		if (this.times > 0) {
			this.times--;
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doAfterBody() throws JspException {
		indexNum++;
		if (StringUtils.isNotEmpty(this.index)) {
			request.setAttribute(this.index, this.indexNum);
		}
		if (this.times > 0) {
			this.times--;
			// 表示双从标签开始输入
			return EVAL_BODY_AGAIN;
		}
		// 表示结束，忽略标签内部的内容
		return SKIP_BODY;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}


}