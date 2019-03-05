/**
 * 
 */
package com.hp.core.database.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.hp.core.common.beans.BaseBean;
import com.hp.core.common.utils.StringUtil;
import com.hp.core.database.enums.DirectionEnum;

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
	@Deprecated
	private String sortColumn;
	
	/**
	 * 排序方式
	 */
	@Deprecated
	private String order = "ASC";
	
	/**
	 * 查询的开始行数
	 */
	private int startIndex = 0;
	
	/**
	 * 排序
	 */
	private List<OrderBy> orderBy;
	
	public PageModel() {}
	
	public PageModel(int currentPage, int pageSize) {
		setCurrentPage(currentPage);
		setPageSize(pageSize);
	}
	
	public PageModel(int currentPage, int pageSize, OrderBy... orderBy) {
		this(currentPage, pageSize);
		if (ArrayUtils.isEmpty(orderBy)) {
			return;
		}
		for (OrderBy o : orderBy) {
			addOrderBy(o);
		}
	}

	public PageModel(int currentPage, int pageSize, String sortColumn) {
		this(currentPage, pageSize, OrderBy.of(sortColumn));
	}
	
	public static PageModel of(int currentPage, int pageSize, OrderBy... orderBy) {
		return new PageModel(currentPage, pageSize, orderBy);
	}
	
	public static PageModel of(int currentPage, int pageSize, String sortColumn) {
		return new PageModel(currentPage, pageSize, sortColumn);
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageModel setPageSize(int pageSize) {
		this.pageSize = pageSize;
		setStartIndex((this.currentPage - 1) * this.pageSize);
		return this;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	@Deprecated
	public PageModel setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
		if (StringUtils.isNotEmpty(sortColumn)) {
			this.sortColumn = StringUtil.toDBColumn(sortColumn);
			initOrderBy();
			if (this.orderBy.size() == 0) {
				this.addOrderBy(OrderBy.of(this.sortColumn));
			} else if (this.orderBy.size() == 1) {
				this.orderBy.get(0).setFieldName(this.sortColumn);
			}
		}
		return this;
	}

	public String getOrder() {
		return order;
	}

	@Deprecated
	public PageModel setOrder(String order) {
		this.order = order;
		initOrderBy();
		DirectionEnum d = DirectionEnum.getDirectionEnumByText(this.order);
		if (this.orderBy.size() == 0) {
			this.addOrderBy(OrderBy.of(null, d));
		} else if (this.orderBy.size() == 1) {
			this.orderBy.get(0).setDirection(d);
		}
		return this;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public PageModel setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public PageModel setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
		setStartIndex((this.currentPage - 1) * this.pageSize);
		return this;
	}

	public List<OrderBy> getOrderBy() {
		return orderBy;
	}

	public PageModel setOrderBy(List<OrderBy> orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	
	public PageModel addOrderBy(OrderBy... orderBy) {
		initOrderBy();
		if (ArrayUtils.isEmpty(orderBy) || orderBy[0] == null) {
			return this;
		}
		this.orderBy.addAll(Lists.newArrayList(orderBy));
		return this;
	}
	
	private void initOrderBy() {
		if (this.orderBy == null) {
			this.orderBy = new ArrayList<>();
		}
	}
}
