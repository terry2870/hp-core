/**
 * 
 */
package com.hp.core.mybatis.provider;

import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.hp.core.mybatis.bean.DAOInterfaceInfoBean;
import com.hp.core.mybatis.bean.DynamicEntityBean;
import com.hp.core.mybatis.bean.GenericParadigmBean;
import com.hp.core.mybatis.interceptor.DAOMethodInterceptorHandle;

/**
 * @author huangping 2018年5月21日
 */
public class BaseSelectProvider {

	/**
	 * 无条件，查询总数
	 * @return
	 */
	public static String selectAllCount() {
		DynamicEntityBean entity = getEntity();
		return new SQL()
				.SELECT("count(*)")
				.FROM(entity.getTableName())
				.toString();
	}

	/**
	 * 根据主键查询
	 * @param param
	 * @return
	 */
	public static String selectByPrimaryKey() {
		DynamicEntityBean entity = getEntity();
		return new SQL()
				.SELECT(entity.getSelectColumns())
				.FROM(entity.getTableName())
				.WHERE(entity.getPrimaryKeyColumnName() + "=#{id}")
				.toString();
	}
	
	public static String selectCountByParams(Map<String, Object> params) {
		DynamicEntityBean entity = getEntity();
		return "";
	}
	
	/**
	 * 获取dal对象实体
	 * @return
	 */
	private static DynamicEntityBean getEntity() {
		DAOInterfaceInfoBean info = DAOMethodInterceptorHandle.getRouteDAOInfo();
		BaseProviderFactory providerFactory = BaseProviderFactory.getInstance();
		
		//获取泛型对象
		GenericParadigmBean genericParadigmBean = providerFactory.getGenericParadigmByClass(info.getClassName());
		
		//获取表名和所有字段
		DynamicEntityBean entity = providerFactory.getDynamicEntityByClass(genericParadigmBean.getTargetModelClassName());
		return entity;
	}
	
	
}
