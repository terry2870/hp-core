/**
 * 
 */
package com.hp.core.database.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.database.enums.QueryTypeEnum;

/**
 * @author huangping
 * Aug 2, 2019
 */
public class SQLBuilders extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6812948703549369091L;
	
	private List<SQLBuilder> sqlBuilders = new ArrayList<>();

	/**
	 * 等于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders eq(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value));
		return this;
	}
	
	/**
	 * 不等于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders not_eq(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.NOT_EQUALS));
		return this;
	}
	
	/**
	 * 前缀匹配
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders prefix(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.PREFIX));
		return this;
	}
	
	/**
	 * 后缀匹配
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders suffix(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.SUFFIX));
		return this;
	}
	
	/**
	 * 模糊匹配
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders like(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.LIKE));
		return this;
	}
	
	/**
	 * in
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders in(String field, Object value) {
		if (value == null) {
			return this;
		}
		if (value instanceof Collection) {
			Collection<?> list = (Collection<?>) value;
			sqlBuilders.add(SQLBuilder.of(field, StringUtils.join(list, ","), QueryTypeEnum.IN));
		} else if (value instanceof Object[]) {
			Object[] arr = (Object[]) value;
			sqlBuilders.add(SQLBuilder.of(field, StringUtils.join(arr, ","), QueryTypeEnum.IN));
		} else {
			sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.IN));
		}
		return this;
	}
	
	/**
	 * not in
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders not_in(String field, Object value) {
		if (value == null) {
			return this;
		}
		if (value instanceof Collection) {
			Collection<?> list = (Collection<?>) value;
			sqlBuilders.add(SQLBuilder.of(field, StringUtils.join(list, ","), QueryTypeEnum.NOT_IN));
		} else if (value instanceof Object[]) {
			Object[] arr = (Object[]) value;
			sqlBuilders.add(SQLBuilder.of(field, StringUtils.join(arr, ","), QueryTypeEnum.NOT_IN));
		} else {
			sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.NOT_IN));
		}
		return this;
	}
	
	/**
	 * 大于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders gt(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.GT));
		return this;
	}
	
	/**
	 * 大于等于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders gte(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.GTE));
		return this;
	}
	
	/**
	 * 小于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders lt(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.LT));
		return this;
	}
	
	/**
	 * 小于等于
	 * @param field
	 * @param value
	 * @return
	 */
	public SQLBuilders lte(String field, Object value) {
		sqlBuilders.add(SQLBuilder.of(field, value, QueryTypeEnum.LTE));
		return this;
	}
	
	/**
	 * 添加build
	 * @param sqlBuilder
	 * @return
	 */
	public SQLBuilders addSQLBuild(SQLBuilder sqlBuilder) {
		sqlBuilders.add(sqlBuilder);
		return this;
	}
	
	/**
	 * 构造返回
	 * @return
	 */
	public SQLBuilder[] build() {
		return sqlBuilders.toArray(new SQLBuilder[] {});
	}
}
