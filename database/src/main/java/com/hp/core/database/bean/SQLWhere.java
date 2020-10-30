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
 * Jul 2, 2020
 */
public class SQLWhere extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4691759724036903119L;

	/**
	 * 数据库字段名称
	 */
	private String field;
	
	/**
	 * 值
	 */
	private Object value;
	
	/**
     * Java数据类型
     */
    private Class<?> javaType = Object.class;
	
	/**
	 * 运算符
	 */
	private QueryTypeEnum operator = QueryTypeEnum.EQUALS;
	
	private SQLWhere() {}
	
	private SQLWhere(String field, Object value) {
		this.field = field;
		this.value = value;
	}
	
	private SQLWhere(String field, Object value, QueryTypeEnum operator) {
		this(field, value);
		this.operator = operator;
	}
	
	private static SQLWhere of(String field, Object value, QueryTypeEnum operator) {
		return new SQLWhere(field, value, operator);
	}
	
	public static SQLWhereBuilder builder() {
		return new SQLWhereBuilder();
	}
	
	public static class SQLWhereBuilder extends BaseBean {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2688761243553292048L;
		
		private SQLWhereBuilder() {}
		
		private List<SQLWhere> where = new ArrayList<>();
		
		/**
		 * 等于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder eq(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.EQUALS));
			return this;
		}
		
		/**
		 * 不等于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder not_eq(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.NOT_EQUALS));
			return this;
		}
		
		/**
		 * 前缀匹配
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder prefix(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.PREFIX));
			return this;
		}
		
		/**
		 * 后缀匹配
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder suffix(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.SUFFIX));
			return this;
		}
		
		/**
		 * 模糊匹配
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder like(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.LIKE));
			return this;
		}
		
		/**
		 * in
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder in(String field, Object value) {
			if (value == null) {
				return this;
			}
			if (value instanceof Collection) {
				Collection<?> list = (Collection<?>) value;
				where.add(SQLWhere.of(field, StringUtils.join(list, ","), QueryTypeEnum.IN));
			} else if (value instanceof Object[]) {
				Object[] arr = (Object[]) value;
				where.add(SQLWhere.of(field, StringUtils.join(arr, ","), QueryTypeEnum.IN));
			} else {
				where.add(SQLWhere.of(field, value, QueryTypeEnum.IN));
			}
			return this;
		}
		
		/**
		 * not in
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder not_in(String field, Object value) {
			if (value == null) {
				return this;
			}
			if (value instanceof Collection) {
				Collection<?> list = (Collection<?>) value;
				where.add(SQLWhere.of(field, StringUtils.join(list, ","), QueryTypeEnum.NOT_IN));
			} else if (value instanceof Object[]) {
				Object[] arr = (Object[]) value;
				where.add(SQLWhere.of(field, StringUtils.join(arr, ","), QueryTypeEnum.NOT_IN));
			} else {
				where.add(SQLWhere.of(field, value, QueryTypeEnum.NOT_IN));
			}
			return this;
		}
		
		/**
		 * 大于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder gt(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.GT));
			return this;
		}
		
		/**
		 * 大于等于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder gte(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.GTE));
			return this;
		}
		
		/**
		 * 小于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder lt(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.LT));
			return this;
		}
		
		/**
		 * 小于等于
		 * @param field
		 * @param value
		 * @return
		 */
		public SQLWhereBuilder lte(String field, Object value) {
			where.add(SQLWhere.of(field, value, QueryTypeEnum.LTE));
			return this;
		}
		
		public List<SQLWhere> getWhere() {
			return where;
		}
		
		public List<SQLWhere> build() {
			return where;
		}
		
		public SQLWhere[] buildArray() {
			return where.toArray(new SQLWhere[] {});
		}
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	public QueryTypeEnum getOperator() {
		return operator;
	}

	public void setOperator(QueryTypeEnum operator) {
		this.operator = operator;
	}
}
