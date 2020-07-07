/**
 * 
 */
package com.hp.core.elasticsearch.index.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.elasticsearch.bean.IndexInfo;

/**
 * 使用查询最小，最大id的方式，来创建索引
 * @author huangping
 * Apr 8, 2019
 */
public abstract class AbstMinMaxIdIndexServiceImpl<T, E> extends AbstSimpleIndexServiceImpl<T, E> {

	private static Logger log = LoggerFactory.getLogger(AbstMinMaxIdIndexServiceImpl.class);
	
	/**
	 * 获取最小的id
	 * @param wheres
	 * @return
	 */
	protected int getMinId(SQLWhere[] wheres) {
		return baseMapper.selectMinId(wheres);
	}
	
	/**
	 * 获取最大的id
	 * @param wheres
	 * @return
	 */
	protected int getMaxId(SQLWhere[] wheres) {
		return baseMapper.selectMaxId(wheres);
	}
	
	/**
	 * 从数据库获取数据
	 * @param minId
	 * @param maxId
	 * @param builders
	 * @return
	 */
	protected List<T> getDataListFromDB(int minId, int maxId, SQLBuilders builders) {
		return baseMapper.selectListByRange(minId, maxId, builders);
	}
	
	/**
	 * 插入数据到新的索引
	 * @param indexInfo
	 * @param newIndexName
	 */
	@Override
	public void insertIntoES(IndexInfo indexInfo, IndexCoordinates newIndexCoordinates) {
		SQLBuilders builders = getSQLBuilders();
		SQLWhere[] wheres = CollectionUtils.isEmpty(builders.getWhereList()) ? null : builders.getWhereList().toArray(new SQLWhere[] {});
		//最大id
		int min = getMinId(wheres);
		//最小id
		long max = getMaxId(wheres);
		if (max == 0 || max < min) {
			log.warn("reBuildIndex error. with maxid is error. with indexInfo={}, min={}, max={}", indexInfo, min, max);
			return;
		}
		
		int start = min;
		List<T> list = null;
		List<IndexQuery> queries = null;
		while (start <= max) {
			log.info("insert to es data indexName={}. {}/{}/{}", indexInfo.getIndexName(), min, start, max);
			
			//获取列表
			list = getDataListFromDB(start, start + getSize(), builders);
			
			//开始id自加
			start = start + getSize();
			
			if (CollectionUtils.isEmpty(list)) {
				//为空，则下一轮
				continue;
			}
			
			//组装成索引对象
			queries = getIndexQueryDataListByDataList(list);
			
			if (CollectionUtils.isEmpty(queries)) {
				continue;
			}

			//批量插入到新索引
			elasticsearchRestTemplate.bulkIndex(queries, newIndexCoordinates);
		}
	}

}
