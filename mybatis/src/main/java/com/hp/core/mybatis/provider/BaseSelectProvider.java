/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.mybatis.bean.DynamicColumnBean;
import com.hp.core.mybatis.bean.DynamicEntityBean;
import com.hp.core.mybatis.constant.SQLProviderConstant;
import com.hp.core.mybatis.exceptions.ProviderSQLException;

/**
 * @author huangping 2018年5月21日
 */
public class BaseSelectProvider {

	private static Logger log = LoggerFactory.getLogger(BaseSelectProvider.class);
	
	/**
	 * 无条件，查询总数
	 * @return
	 */
	public static String selectAllCount() {
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
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
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		SQL sql = new SQL()
				.SELECT(entity.getSelectColumns())
				.FROM(entity.getTableName())
				.WHERE(entity.getPrimaryKeyColumnName() + "=#{id}");
		log.debug("selectByPrimaryKey get sql \r\nsql={} \r\nid={}  \r\nentity={}", sql, id, entity);
		return sql.toString();
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
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		
		SQL sql = new SQL()
				.SELECT("count(*)")
				.FROM(entity.getTableName());
		
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
		DynamicEntityBean entity = BaseSQLProviderFactory.getEntity();
		
		SQL sql = new SQL()
				.SELECT(entity.getSelectColumns())
				.FROM(entity.getTableName());
		setSQLByParams(target.get(SQLProviderConstant.TARGET_OBJECT_ALIAS), entity, sql);
		
		String sql1 = sql.toString() + getPageSQL((PageModel) target.get(SQLProviderConstant.PAGE_OBJECT_ALIAS), entity);
		
		log.debug("selectListByParams get sql \r\nsql={} \r\nparams={}, \r\nentity={}", sql1, target, entity);
		return sql1;
	}
	
	/**
	 * 获取分页sql
	 * (由于mybatis的SQL对象不支持 append 方法，所以这里只能这样处理)
	 * @param page
	 * @param entity
	 * @return
	 */
	private static String getPageSQL(PageModel page, DynamicEntityBean entity) {
		if (page == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(page.getSortColumn())) {
			sb.append(" order by ").append(page.getSortColumn()).append(" ").append(page.getOrder());
		}
		if (page.getPageSize() > 0) {
			sb.append(" limit ").append(page.getStartIndex()).append(", ").append(page.getPageSize());
		}
		return sb.toString();
	}
	
	/**
	 * 设置查询条件
	 * @param params
	 * @param entity
	 * @param sql
	 */
	private static void setSQLByParams(Object params, DynamicEntityBean entity, SQL sql) {
		String key = null;
		Object value = null;
		try {
			for (DynamicColumnBean column : entity.getColumns()) {
				key = column.getFieldName();
				value = BeanUtils.getProperty(params, key);
				if (value == null) {
					//为空，跳过
					continue;
				}
				
				sql.WHERE(column.getColumnName() + " = #{"+ SQLProviderConstant.TARGET_OBJECT_ALIAS +"."+ column.getFieldName() +"}");
			}
		} catch (Exception e) {
			log.error("get setSQLByParams sql error. with params is {}", params, e);
		}
	}
	
}
