/**
 * 
 */
package com.hp.core.database.datasource;
/**
 * @author huangping
 * 2018年4月2日
 */

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.database.bean.DatasourceConfigBean;
import com.hp.core.database.bean.DynamicDatasourceBean;
import com.hp.core.database.enums.DatabaseTypeEnum;
import com.hp.core.database.exceptions.MasterUrlNotFoundException;

public interface AbstConnectionPoolFactory {
	
	static Logger log = LoggerFactory.getLogger(AbstConnectionPoolFactory.class);

	/**
	 * 获取连接池对象
	 * @param bean
	 * @return
	 */
	public default DynamicDatasourceBean getDynamicDatasource(DatasourceConfigBean bean) {
		if (bean == null || CollectionUtils.isEmpty(bean.getMasterIpPort())) {
			log.error("getDatasource error. with masterIpPort is empty");
			throw new MasterUrlNotFoundException();
		}
		
		DynamicDatasourceBean result = new DynamicDatasourceBean();
		DataSource ds = null;
		AbstDatabase database = DatabaseTypeEnum.getDatabaseByDatabaseType(bean.getDatabaseType());
		
		List<DataSource> masterDatasource = new ArrayList<>(bean.getMasterIpPort().size());
		
		//处理master的数据源
		for (String url : bean.getMasterIpPort()) {
			ds = getDatasource(bean, database, url);
			masterDatasource.add(ds);
		}
		result.setMasterDatasource(masterDatasource);
		
		//处理slave数据源
		if (CollectionUtils.isNotEmpty(bean.getSlaveIpPort())) {
			List<DataSource> slaveDatasource = new ArrayList<>(bean.getSlaveIpPort().size());
			for (String url : bean.getSlaveIpPort()) {
				ds = getDatasource(bean, database, url);
				slaveDatasource.add(ds);
			}
			result.setSlaveDatasource(slaveDatasource);
		}
		return result;
	}
	
	/**
	 * 获取基础信息
	 * @param bean
	 * @param database
	 * @param ipPort
	 * @return
	 */
	public DataSource getDatasource(DatasourceConfigBean bean, AbstDatabase database, String ipPort);
}
