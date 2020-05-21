/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilder;
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
		String sql = getSQL(target, "max("+ entity.getPrimaryKeyColumnName() +")");
		log.debug("selectMaxId get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 查询最小id
	 * @return
	 */
	public static String selectMinId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(target, "min("+ entity.getPrimaryKeyColumnName() +")");
		log.debug("selectMinId get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 根据id范围查询
	 * @param target
	 * @return
	 */
	public static String selectListByRange(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(target, entity.getSelectColumns(), entity.getPrimaryKeyColumnName() + " >= #{minId}", entity.getPrimaryKeyColumnName() + " < #{maxId}");
		log.debug("selectListByRange get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 查询总数
	 * @return
	 */
	public static String selectCount(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(target, "count(*)");
		log.debug("selectAllCount get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public static String selectByPrimaryKey(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(new HashMap<>(), entity.getSelectColumns(), entity.getPrimaryKeyColumnName() + "=#{id}");
		log.debug("selectByPrimaryKey get sql \r\nsql={} \r\ntarget={}  \r\nentity={}", sql, target, entity);
		return sql.toString();
	}
	
	/**
	 * 根据主键，批量查询
	 * @param target
	 * @return
	 */
	public static String selectByPrimaryKeys(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder inSQL = new StringBuilder();
		inSQL.append(entity.getPrimaryKeyColumnName())
			.append(" IN (")
			.append("")
			;
		List<?> list = (List<?>) target.get("list");
		for (int i = 0; i < list.size(); i++) {
			inSQL.append("#{list[").append(i).append("]}");
			if (i != list.size() - 1) {
				inSQL.append(", ");
			}
		}
		inSQL.append(")");
		
		String sql = getSQL(target, entity.getSelectColumns(), inSQL.toString());
		log.debug("selectByPrimaryKeys get sql \r\nsql={} \r\nlist={}  \r\nentity={}", sql, list.size(), entity);
		return sql;
	}
	
	/**
	 * 根据主键，批量查询（并且按照list里面id顺序排序）
	 * @param target
	 * @return
	 */
	public static String selectByPrimaryKeysWithInSort(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = selectByPrimaryKeys(target);
		List<?> list = (List<?>) target.get("list");
		sql += " order by field ("+ entity.getPrimaryKeyColumnName() +", "+ StringUtils.join(list, ",") +")";
		log.debug("selectByPrimaryKeysWithInSort get sql \r\nsql={} \r\nlist={}  \r\nentity={}", sql, list.size(), entity);
		return sql;
	}
	
	/**
	 * 查询列表
	 * @param target
	 * @return
	 */
	public static String selectList(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(target, entity.getSelectColumns());
		log.debug("selectList get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 根据传入的sqlbuild，查询一个
	 * @param target
	 * @return
	 */
	public static String selectOne(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = selectList(target);
		sql += " LIMIT 1";
		log.debug("selectOne get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql;
	}
	
	/**
	 * 查询大于该id的数据
	 * @param target
	 * @return
	 */
	public static String selectPageListLargeThanId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		
		String sql = getSQL(target, entity.getSelectColumns(), entity.getPrimaryKeyColumnName() + " > #{largeThanId}");
		log.debug("selectPageListLargeThanId get sql \r\nsql={} \r\ntarget={}, \r\nentity={}", sql, target, entity);
		return sql;
	}
	
	/**
	 * 查询主键列表
	 * @param target
	 * @return
	 */
	public static String selectPrimaryKeyList(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		String sql = getSQL(target, entity.getPrimaryKeyColumnName());
		log.debug("selectPrimaryKeyList get sql \r\nsql={} \r\ntarget={}, \r\nentity={}", sql, target, entity);
		return sql;
	}
	
	/**
	 * 生成sql
	 * @param target
	 * @return
	 */
	private static String getSQL(Map<String, Object> target, String selected, String... query) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		StringBuilder sql = new StringBuilder()
				.append("select ")
				.append(selected)
				.append(" from ")
				.append(entity.getTableName())
				.append(" where 1=1 ");
		//额外查询条件
		if (ArrayUtils.isNotEmpty(query)) {
			for (String q : query) {
				sql.append(" and ").append(q);
			}
		}
		
		//设置查询条件
		if (target.containsKey(SQLProviderConstant.SQL_BUILD_ALIAS)) {
			sql.append(SQLBuilderHelper.getSQLBySQLBuild((SQLBuilder[]) target.get(SQLProviderConstant.SQL_BUILD_ALIAS)));
		}
		//setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		
		//排序
		if (target.containsKey(SQLProviderConstant.ORDER_BY)) {
			getOrderBy((OrderBy[]) target.get(SQLProviderConstant.ORDER_BY), sql);
		}
		
		//分页
		if (target.containsKey(SQLProviderConstant.PAGE_OBJECT_ALIAS)) {
			getPageSQL((PageModel) target.get(SQLProviderConstant.PAGE_OBJECT_ALIAS), sql);
		}
		
		return sql.toString();
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
	
}
