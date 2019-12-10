/**
 * 
 */
package com.hp.core.jsptags.tags;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.hp.core.jsptags.ognl.OgnlCache;


/**
 * @author ping.huang 2017年4月24日
 */
public class ForEachTag extends BaseTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5780207403658277444L;

	static Logger log = LoggerFactory.getLogger(ForEachTag.class);

	
	private int startIndex;
	private int endIndex;
	private String item = DEFAULT_ATTRIBUTE_NAME;
	private String index = DEFAULT_INDEX_ATTRIBUTE_NAME;
	private String total = DEFAULT_TOTAL_ATTRIBUTE_NAME;
	
	private Collection<Object> list = null;
	private Iterator<Object> it = null;
	private int count;
	private Object itemObj;
	
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		super.doStartTag();
		Object obj = null;
		try {
			obj = OgnlCache.getValue(this.name, getDataFromRequest(this.request));
		} catch (Exception e) {
			log.error("doStartTag error. with param is {}", this, e);
			return SKIP_BODY;
		}
		if (obj == null) {
			log.warn("list is null from {}", this.name);
			return SKIP_BODY;
		}
		if (!(obj instanceof Collection) && !(obj instanceof Object[])) {
			log.warn("value is not a list. from {}, and class is {}", this.name, obj.getClass());
			return SKIP_BODY;
		}
		
		if (obj instanceof Collection) {
			this.list = (Collection<Object>) obj;
		} else if (obj instanceof Object[]) {
			//如果是数组，把它转为collection
			this.list = Lists.newArrayList((Object[]) obj);
		}
		
		this.it = list.iterator();
		
		this.request.setAttribute(this.total, this.list.size());
		this.count = 0;
		
		//检查不符合继续下去的条件
		if (!check()) {
			return SKIP_BODY;
		}
		this.itemObj = this.it.next();
		while (this.startIndex > 0 && this.count < this.startIndex) {
			this.itemObj = this.it.next();
			this.count++;
		}
		
		this.request.setAttribute(this.item, this.itemObj);
		if (StringUtils.isNotEmpty(this.index)) {
			request.setAttribute(this.index, this.count);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() throws JspException {
		this.count++;
		if (!check()) {
			return SKIP_BODY;
		}
		
		if (this.startIndex > 0 && this.count < this.startIndex) {
			return EVAL_BODY_AGAIN;
		}
		if (this.endIndex > 0 && this.count > this.endIndex) {
			return SKIP_BODY;
		}
		
		this.itemObj = this.it.next();
		
		this.request.setAttribute(this.item, this.itemObj);
		if (StringUtils.isNotEmpty(this.index)) {
			request.setAttribute(this.index, this.count);
		}
		return EVAL_BODY_AGAIN;
	}

	private boolean check() {
		if (CollectionUtils.isEmpty(this.list) || !this.it.hasNext()) {
			return false;
		}
		if (this.count >= this.list.size()) {
			return false;
		}
		if (this.endIndex > 0 && this.count > this.endIndex) {
			return false;
		}
		return true;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
