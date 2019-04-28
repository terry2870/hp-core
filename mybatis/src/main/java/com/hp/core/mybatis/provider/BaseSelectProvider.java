/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DynamicColumnBean;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.enums.QueryTypeEnum;
import com.hp.core.database.exceptions.ProviderSQLException;
import com.hp.core.database.interceptor.BaseSQLAOPFactory;
import com.hp.core.mybatis.constant.SQLProviderConstant;

/**
 * 基本的查询操作
 * 获取基本的查询操作的sql
 * @author huangping 2018年5月21日
 */
public class BaseSelectProvider {

	private static Logger log = LoggerFactory.getLogger(BaseSelectProvider.class);
	
	/**
	 * 查询最大id
	 * @return
	 */
	public static String selectMaxId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder()
				.append("select max(")
				.append(entity.getPrimaryKeyColumnName())
				.append(") from ")
				.append(entity.getTableName())
				.append(" where 1=1 ");
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		log.debug("selectMaxId get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}
	
	/**
	 * 查询最小id
	 * @return
	 */
	public static String selectMinId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder()
				.append("select min(")
				.append(entity.getPrimaryKeyColumnName())
				.append(") from ")
				.append(entity.getTableName())
				.append(" where 1=1 ");
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		log.debug("selectMinId get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}
	
	/**
	 * 根据id范围查询
	 * @param params
	 * @return
	 */
	public static String selectListByRange(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder()
				.append("select ")
				.append(entity.getSelectColumns())
				.append(" from ")
				.append(entity.getTableName())
				.append(" where ")
				.append(entity.getPrimaryKeyColumnName())
				.append(" >= #{minId} and ")
				.append(entity.getPrimaryKeyColumnName())
				.append(" < #{maxId} ");
		setSQLByParams(params.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		log.debug("selectListByRange get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}
	
	/**
	 * 无条件，查询总数
	 * @return
	 */
	public static String selectAllCount() {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQL sql = new SQL()
				.SELECT("count(*)")
				.FROM(entity.getTableName());
		log.debug("selectAllCount get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public static String selectByPrimaryKey(Object id) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQL sql = new SQL()
				.SELECT(entity.getSelectColumns())
				.FROM(entity.getTableName())
				.WHERE(entity.getPrimaryKeyColumnName() + "=#{id}");
		log.debug("selectByPrimaryKey get sql \r\nsql={} \r\nid={}  \r\nentity={}", sql, id, entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键，批量查询
	 * @param params
	 * @return
	 */
	public static String selectByPrimaryKeys(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		List<?> list = (List<?>) params.get("list");
		StringBuilder sql = new StringBuilder("SELECT ")
				.append("\n")
				.append(entity.getSelectColumns())
				.append("\n")
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE ")
				.append(entity.getPrimaryKeyColumnName())
				.append(" IN (");
		for (int i = 0; i < list.size(); i++) {
			sql.append("#{list[").append(i).append("]}");
			if (i != list.size() - 1) {
				sql.append(", ");
			}
		}
		sql.append(")");
		log.debug("selectByPrimaryKeys get sql \r\nsql={} \r\nlist={}  \r\nentity={}", sql, list.size(), entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param params
	 * @return
	 */
	public static String selectByPrimaryKeysWithInSort(Map<String, Object> params) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = selectByPrimaryKeys(params);
		List<?> list = (List<?>) params.get("list");
		sql += "order by field ("+ entity.getPrimaryKeyColumnName() +", "+ StringUtils.join(list, ",") +")";
		
		log.info("selectByPrimaryKeysWithInSort get sql \r\nsql={} \r\nlist={}  \r\nentity={}", sql, list.size(), entity);
		return sql;
	}
	
	/**
	 * 根据条件，查询数量
	 * @param target
	 * @return
	 */
	public static String selectCountByParams(Map<String, Object> target) {
		if (target == null) {
			return selectAllCount();
		}
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder("SELECT count(*) FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		
		//遍历属性字段，不为空的都加入sql查询条件
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		log.debug("selectCountByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	/**
	 * 根据条件，查询list（分页）
	 * @param target
	 * @return
	 */
	public static String selectListByParams(Map<String, Object> target) {
		if (target == null) {
			log.error("selectListByParams error. with params is null.");
			throw new ProviderSQLException("params is null");
		}
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder("SELECT ")
				.append(entity.getSelectColumns())
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		
		if (target.containsKey(SQLProviderConstant.ORDER_BY)) {
			getOrderBy((OrderBy[]) target.get(SQLProviderConstant.ORDER_BY), sql);
		}
		
		if (target.containsKey(SQLProviderConstant.PAGE_OBJECT_ALIAS)) {
			getPageSQL((PageModel) target.get(SQLProviderConstant.PAGE_OBJECT_ALIAS), sql);
		}
		
		log.debug("selectListByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	/**
	 * 根据条件，查询单个
	 * @param target
	 * @return
	 */
	public static String selectOneByParams(Map<String, Object> target) {
		if (target == null) {
			log.error("selectOneByParams error. with params is null.");
			throw new ProviderSQLException("params is null");
		}
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder("SELECT ")
				.append(entity.getSelectColumns())
				.append(" FROM ")
				.append(entity.getTableName())
				.append(" WHERE 1=1");
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		
		sql.append(" limit 1");
		
		log.debug("selectOneByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	public static String selectListBySQL(Map<String, Object> params) {
		log.info("params= {}", params);
		
		
		return "";
	}
	
	/**
	 * 获取分页sql
	 * (由于mybatis的SQL对象不支持 append 方法，所以这里只能这样处理)
	 * @param page
	 * @param sql
	 */
	private static void getPageSQL(PageModel page, StringBuilder sql) {
		if (page == null) {
			return;
		}
		
		if (CollectionUtils.isNotEmpty(page.getOrderBy())) {
			getOrderBy(page.getOrderBy().toArray(new OrderBy[] {}), sql);
		}
		
		if (page.getPageSize() > 0) {
			sql.append(" limit ").append(page.getStartIndex()).append(", ").append(page.getPageSize());
		}
	}
	
	/**
	 * 排序
	 * @param orderBy
	 * @param sql
	 */
	private static void getOrderBy(OrderBy[] orderBy, StringBuilder sql) {
		if (ArrayUtils.isEmpty(orderBy) || orderBy[0] == null) {
			return;
		}
		List<String> orderByStringList = new ArrayList<>();
		for (OrderBy o : orderBy) {
			if (StringUtils.isEmpty(o.getFieldName())) {
				continue;
			}
			orderByStringList.add(o.toString());
		}
		if (CollectionUtils.isNotEmpty(orderByStringList)) {
			sql.append(" order by ").append(StringUtils.join(orderByStringList, ", "));
		}
	}
	
	/**
	 * 设置查询条件
	 * @param params
	 * @param entity
	 * @param sql
	 */
	private static void setSQLByParams(Object params, DynamicEntityBean entity, StringBuilder sql) {
		if (params == null) {
			return;
		}
		String key = null;
		Object value = null;
		try {
			for (DynamicColumnBean column : entity.getColumns()) {
				key = column.getFieldName();
				value = BeanUtils.getProperty(params, key);
				if (checkNull(column, value)) {
					//为空，跳过
					continue;
				}
				if (QueryTypeEnum.EQUALS.equals(column.getQueryType())) {
					sql.append(" AND ")
						.append(column.getColumnName())
						.append(" = #{")
						.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
						.append(".")
						.append(column.getFieldName())
						.append("}");
				} else if (QueryTypeEnum.LIKE.equals(column.getQueryType())) {
					sql.append(" AND INSTR(")
						.append(column.getColumnName())
						.append(", #{")
						.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
						.append(".")
						.append(column.getFieldName())
						.append("}) > 0");
				} else if (QueryTypeEnum.IN.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" IN (").append(value).append(")");
				} else if (QueryTypeEnum.NOT_IN.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" NOT IN (").append(value).append(")");
				} else if (QueryTypeEnum.GT.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" > #{")
					.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
					.append(".")
					.append(column.getFieldName())
					.append("}");
				} else if (QueryTypeEnum.LT.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" < #{")
					.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
					.append(".")
					.append(column.getFieldName())
					.append("}");
				} else if (QueryTypeEnum.GTE.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" >= #{")
					.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
					.append(".")
					.append(column.getFieldName())
					.append("}");
				} else if (QueryTypeEnum.LTE.equals(column.getQueryType())) {
					sql.append(" AND ")
					.append(column.getColumnName())
					.append(" <= #{")
					.append(SQLProviderConstant.TARGET_OBJECT_ALIAS)
					.append(".")
					.append(column.getFieldName())
					.append("}");
				}
				
			}
		} catch (Exception e) {
			log.error("get setSQLByParams sql error. with params is {}", params, e);
		}
	}
	
	private static boolean checkNull(DynamicColumnBean column, Object value) {
		if (value == null) {
			//null
			return true;
		}
		if (String.class.getName().equals(column.getJavaType().getName()) && StringUtils.isEmpty((String) value)) {
			//空字符串
			return true;
		}
		return false;
	}
	
}
