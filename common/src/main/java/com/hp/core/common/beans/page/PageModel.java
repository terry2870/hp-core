/**
 * 
 */
package com.hp.core.common.beans.page;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.StringUtil;

/**
 * @author huangping
 * 2016年8月25日 下午11:58:19
 */
public class PageModel extends BaseBean {

	private static final long serialVersionUID = -6321131810635063357L;

	/**
	 * 页码，默认是第一页
	 */
	private int currentPage = 1;
	
	/**
	 * 每页显示的记录数，默认是10
	 */
	private int pageSize = 10;
	
	/**
	 * 排序字段
	 */
	private String sortColumn;
	
	/**
	 * 排序方式
	 */
	private String order = "ASC";
	
	/**
	 * 查询的开始行数
	 */
	private int startIndex = 0;


	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		setStartIndex((this.currentPage - 1) * this.pageSize);
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
		if (StringUtils.isNotEmpty(sortColumn)) {
			this.sortColumn = StringUtil.toDBColumn(sortColumn);
		}
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
		setStartIndex((this.currentPage - 1) * this.pageSize);
	}
}
