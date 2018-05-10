package com.hp.core.common.beans.page;

import java.util.ArrayList;
import java.util.List;

import com.hp.core.common.beans.BaseBean;


/**
 * 统一的分页响应对象 描述：
 * 
 * @author ping.huang 2016年1月25日
 */
public class PageResponse<T> extends BaseBean {

	private static final long serialVersionUID = -6835515582292217174L;

	private int total;
	private int currentPage;
	private int pageSize;
	private int totalPage;
	private List<T> rows = new ArrayList<>();
	private List<T> footer = new ArrayList<>();

	public PageResponse() {
	}
	
	public PageResponse(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public PageResponse(int total, List<T> rows, List<T> footer) {
		this(total, rows);
		this.footer = footer;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		// 在设置总页数的时候计算出对应的总页数
		dealTotalPage();
	}
	
	private void dealTotalPage() {
		if (total > 0 && pageSize > 0) {
			int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
			this.setTotalPage(totalPage);
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}
}
