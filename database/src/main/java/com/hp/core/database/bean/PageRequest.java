package com.hp.core.database.bean;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.StringUtil;

/**
 * 统一的分页请求对象 描述：
 * 
 * @author ping.huang 2016年1月25日
 */
public class PageRequest extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4026346032261456086L;

	/**
	 * 当前页数
	 */
	private int page = 1;
	
	/**
	 * 每页条数
	 */
	private int rows = 10;
	
	/**
	 * 每页条数
	 * rows是用在easyui中的分页参数，其他情况下使用limit
	 */
	private int limit = 10;
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	/**
	 * 排序方式
	 */
	private String order = "ASC";
	
	/**
	 * 查询的开始行数
	 */
	private int startIndex = 0;
	
	
	@SuppressWarnings("deprecation")
	public PageModel toPageModel() {
		PageModel model = new PageModel();
		model.setCurrentPage(this.page);
		model.setOrder(this.order);
		model.setPageSize(this.rows);
		model.setSortColumn(this.sort);
		return model;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
		} else {
			this.page = page;
		}
		setStartIndex((this.page - 1) * this.rows);
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		setStartIndex((this.page - 1) * this.rows);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
		if (StringUtils.isNotEmpty(sort)) {
			this.sort = StringUtil.toDBColumn(sort);
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	
}
