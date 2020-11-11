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
	
	private boolean hasMore;

	public PageResponse() {
	}

	public PageResponse(int currentPage, int pageSize) {
		setCurrentPage(currentPage);
		setPageSize(pageSize);
	}
	
	public PageResponse(int total, List<T> rows) {
		setTotal(total);
		setRows(rows);
	}
	
	public PageResponse(int total, List<T> rows, List<T> footer) {
		this(total, rows);
		this.footer = footer;
	}
	
	public PageResponse(int total, List<T> rows, int currentPage, int pageSize) {
		this(currentPage, pageSize);
		setTotal(total);
		setRows(rows);
	}
	
	public PageResponse(int total, List<T> rows, int currentPage, int pageSize, List<T> footer) {
		this(total, rows, currentPage, pageSize);
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
		if (this.totalPage > 0) {
			//当已经设置过totalPage，则无需重复设置
			return;
		}
		if (total <= 0 || pageSize <= 0) {
			//当总数和每页条数未设置时，跳过
			return;
		}
		int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		this.setTotalPage(totalPage);
		this.setHasMore(totalPage > currentPage);
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
		// 在设置煤业条数时的时候计算出对应的总页数
		dealTotalPage();
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}
	
	public void addRow(T t) {
		if (this.rows == null) {
			this.rows = new ArrayList<>();
		}
		this.rows.add(t);
	}
	
	public void addFooter(T t) {
		if (this.footer == null) {
			this.footer = new ArrayList<>();
		}
		this.footer.add(t);
	}
}
