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
import com.hp.core.database.enums.DirectionEnum;

/**
 * @author huangping
 * Aug 2, 2019
 */
public class SQLBuilders extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6812948703549369091L;
	
	private List<String> selectList = null;
	private List<SQLWhere> whereList = null;
	private PageModel page = null;
	private List<OrderBy> orderbyList = null;
	private String sqlWherePrefix;

	private SQLBuilders() {}
	
	public static SQLBuilders create() {
		return new SQLBuilders();
	}
	
	public SQLBuilders withSqlWherePrefix(String sqlWherePrefix) {
		this.sqlWherePrefix = sqlWherePrefix;
		return this;
	}
	
	/**
	 * 查询字段
	 * @param select
	 * @return
	 */
	public SQLBuilders withSelect(String... select) {
		if (ArrayUtils.isEmpty(select) || StringUtils.isEmpty(select[0])) {
			return this;
		}
		selectList = Lists.newArrayList(select);
		return this;
	}
	
	/**
	 * 查询条件
	 * @param whereList
	 * @return
	 */
	public SQLBuilders withWhere(List<SQLWhere> whereList) {
		this.whereList = whereList;
		return this;
	}
	
	public SQLBuilders withPage(PageModel page) {
		this.page = page;
		return this;
	}
	
	public SQLBuilders withPage(int page, int limit) {
		return withPage(PageModel.of(page, limit));
	}
	
	public SQLBuilders withOrder(OrderBy... orderby) {
		if (ArrayUtils.isEmpty(orderby) || orderby[0] == null) {
			return this;
		}
		
		orderbyList = Lists.newArrayList(orderby);
		return this;
	}
	
	public SQLBuilders withOrder(String fieldName, DirectionEnum direction) {
		if (StringUtils.isEmpty(fieldName)) {
			return this;
		}
		
		if (this.orderbyList == null) {
			this.orderbyList = new ArrayList<>();
		}
		
		this.orderbyList.add(OrderBy.of(fieldName, direction));
		return this;
	}
	
	public SQLBuilders withOrder(String fieldName, String direction) {
		return withOrder(fieldName, DirectionEnum.getDirectionEnumByText(direction));
	}

	public List<String> getSelectList() {
		return selectList;
	}

	public PageModel getPage() {
		return page;
	}

	public List<OrderBy> getOrderbyList() {
		return orderbyList;
	}

	public List<SQLWhere> getWhereList() {
		return whereList;
	}

	public String getSqlWherePrefix() {
		return sqlWherePrefix;
	}
}
