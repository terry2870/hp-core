/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.hp.core.database.bean.DynamicEntityBean;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
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
	 * 查询总数
	 * @return
	 */
	public static String selectCount(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		@SuppressWarnings("unchecked")
		List<SQLWhere> whereList = (List<SQLWhere>) target.get(SQLProviderConstant.SQL_WHERE_ALIAS);
		SQLBuilders builders = SQLBuilders.emptyBuilder()
				.withSqlWherePrefix(SQLProviderConstant.SQL_WHERE_ALIAS)
				.withSelect("COUNT(1)")
				.withWhere(whereList);
		String sql = getSQL(builders, entity);
		log.debug("selectAllCount get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql.toString();
	}
	
	/**
	 * 查询列表
	 * @param target
	 * @return
	 */
	public static String selectList(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQLBuilders builders = (SQLBuilders) target.get(SQLProviderConstant.SQL_BUILDS_ALIAS);
		String sql = getSQL(builders, entity);
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
		SQLBuilders builders = (SQLBuilders) target.get(SQLProviderConstant.SQL_BUILDS_ALIAS);
		builders.withPage(0, 1);
		String sql = getSQL(builders, entity);
		log.debug("selectOne get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql, target, entity);
		return sql;
	}
	
	/**
	 * 查询最大id
	 * @return
	 */
	public static String selectMaxId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		List<SQLWhere> whereList = getSpecialSQLWheres(target);
		SQLBuilders builders = SQLBuilders.emptyBuilder()
				.withSqlWherePrefix(SQLProviderConstant.SQL_WHERE_ALIAS)
				.withSelect("MAX("+ entity.getPrimaryKeyColumnName() +")")
				.withWhere(whereList);
		String sql = getSQL(builders, entity);
		log.debug("selectMaxId get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 查询最小id
	 * @return
	 */
	public static String selectMinId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		List<SQLWhere> whereList = getSpecialSQLWheres(target);
		SQLBuilders builders = SQLBuilders.emptyBuilder()
				.withSqlWherePrefix(SQLProviderConstant.SQL_WHERE_ALIAS)
				.withSelect("MIN("+ entity.getPrimaryKeyColumnName() +")")
				.withWhere(whereList);
		String sql = getSQL(builders, entity);
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
		SQLBuilders builders = getSpecialSQLBuilders(target)
				.withSqlWherePrefix(SQLProviderConstant.SQL_BUILDS_ALIAS + "[0]." + SQLProviderConstant.SQL_WHERE_ALIAS)
				;
		
		String sql = getSQL(builders, entity, entity.getPrimaryKeyColumnName() + " >= #{"+ SQLProviderConstant.MIN_ID_ALIAS +"}", entity.getPrimaryKeyColumnName() + " < #{"+ SQLProviderConstant.MAX_ID_ALIAS +"}");
		log.debug("selectListByRange get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 根据条件，查询大于某一个id值的数据
	 * @param target
	 * @return
	 */
	public static String selectListByLargeThanId(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQLBuilders builders = getSpecialSQLBuilders(target)
				.withSqlWherePrefix(SQLProviderConstant.SQL_BUILDS_ALIAS + "[0]." + SQLProviderConstant.SQL_WHERE_ALIAS)
				;
		
		String sql = getSQL(builders, entity, entity.getPrimaryKeyColumnName() + " > #{"+ SQLProviderConstant.LARGETHAN_ID_OBJECT_ALIAS +"}");
		log.debug("selectListByRange get sql \r\nsql={} \r\nentity={}", sql, entity);
		return sql;
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public static String selectByPrimaryKey(Map<String, Object> target) {
		DynamicEntityBean entity = BaseSQLAOPFactory.getEntity();
		SQLBuilders builders = SQLBuilders.emptyBuilder();
		String sql = getSQL(builders, entity, entity.getPrimaryKeyColumnName() + "=#{id}");
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
		SQLBuilders builders = SQLBuilders.emptyBuilder();
		
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
		String sql = getSQL(builders, entity, inSQL.toString());
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
	 * 生成sql
	 * @param builders
	 * @param entity
	 * @param query
	 * @return
	 */
	private static String getSQL(SQLBuilders builders, DynamicEntityBean entity, String... query) {
		if (builders == null) {
			log.error("getsql error. with builds is empty.");
			return null;
		}
		StringBuilder sql = new StringBuilder()
				.append("select ")
				.append(CollectionUtils.isEmpty(builders.getSelectList()) ? entity.getSelectColumns() : StringUtils.join(builders.getSelectList(), ", "))
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
		if (CollectionUtils.isNotEmpty(builders.getWhereList())) {
			sql.append(SQLBuilderHelper.getSQLBySQLBuild(builders));
		}
		
		//排序
		if (CollectionUtils.isNotEmpty(builders.getOrderbyList())) {
			sql.append(getOrderBy(builders.getOrderbyList()));
		}
		
		//分页
		if (builders.getPage() != null) {
			sql.append(getPageSQL(builders.getPage()));
		}
		
		return sql.toString();
	}
	
	/**
	 * 获取分页sql
	 * (由于mybatis的SQL对象不支持 append 方法，所以这里只能这样处理)
	 * @param page
	 * @param sql
	 */
	private static String getPageSQL(PageModel page) {
		if (page == null) {
			return StringUtils.EMPTY;
		}
		
		StringBuilder sql = new StringBuilder();
		if (CollectionUtils.isNotEmpty(page.getOrderBy())) {
			sql.append(getOrderBy(page.getOrderBy()));
		}
		
		if (page.getPageSize() > 0) {
			sql.append(" LIMIT ").append(page.getStartIndex()).append(", ").append(page.getPageSize());
		}
		
		return sql.toString();
	}
	
	/**
	 * 排序
	 * @param orderBy
	 * @param sql
	 */
	private static String getOrderBy(List<OrderBy> orderBy) {
		if (CollectionUtils.isEmpty(orderBy)) {
			return StringUtils.EMPTY;
		}
		List<String> orderByStringList = new ArrayList<>();
		for (OrderBy o : orderBy) {
			if (StringUtils.isEmpty(o.getFieldName())) {
				continue;
			}
			orderByStringList.add(o.toString());
		}
		if (CollectionUtils.isEmpty(orderByStringList)) {
			return StringUtils.EMPTY;
		} else {
			return " ORDER BY " + StringUtils.join(orderByStringList, ", ");
		}
	}
	
	/**
	 * 获取查询条件list
	 * @param target
	 * @return
	 */
	public static List<SQLWhere> getSpecialSQLWheres(Map<String, Object> target) {
		SQLWhere[] wheres = (SQLWhere[])target.get(SQLProviderConstant.SQL_WHERE_ALIAS);
		if (wheres == null || wheres.length == 0) {
			return null;
		} else {
			return Lists.newArrayList(wheres);
		}
	}
	
	private static SQLBuilders getSpecialSQLBuilders(Map<String, Object> target) {
		SQLBuilders[] builders = (SQLBuilders[])target.get(SQLProviderConstant.SQL_BUILDS_ALIAS);
		if (builders == null || builders.length == 0) {
			return SQLBuilders.emptyBuilder();
		} else {
			return builders[0];
		}
	}
}
