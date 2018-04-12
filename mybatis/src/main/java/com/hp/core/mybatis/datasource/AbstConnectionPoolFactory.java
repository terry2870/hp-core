/**
 * 
 */
package com.hp.core.mybatis.datasource;
/**
 * @author huangping
 * 2018年4月2日
 */

import com.hp.core.mybatis.bean.DatasourceConfigBean;
import com.hp.core.mybatis.bean.DynamicDatasourceBean;

public interface AbstConnectionPoolFactory {

	/**
	 * 获取连接池对象
	 * @param bean
	 * @return
	 */
	public DynamicDatasourceBean getDatasource(DatasourceConfigBean bean);
}
