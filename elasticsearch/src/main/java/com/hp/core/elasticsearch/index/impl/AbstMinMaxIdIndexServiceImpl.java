/**
 * 
 */
package com.hp.core.elasticsearch.index.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

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
	 * @param param
	 * @return
	 */
	protected long getMinId(T param) {
		return baseMapper.selectMinId(param);
	}
	
	/**
	 * 获取最大的id
	 * @param param
	 * @return
	 */
	protected long getMaxId(T param) {
		return baseMapper.selectMaxId(param);
	}
	
	/**
	 * 从数据库获取数据
	 * @param minId
	 * @param maxId
	 * @param param
	 * @return
	 */
	protected List<T> getDataListFromDB(long minId, long maxId, T param) {
		return baseMapper.selectListByRange(minId, maxId, param);
	}
	
	/**
	 * 插入数据到新的索引
	 * @param indexInfo
	 * @param newIndexName
	 */
	@Override
	public void insertIntoES(IndexInfo indexInfo, String newIndexName) {
		T param = getQueryParams();
		//最大id
		long min = getMinId(param);
		//最小id
		long max = getMaxId(param);
		if (max == 0 || max < min) {
			log.warn("reBuildIndex error. with maxid is error. with indexInfo={}, min={}, max={}", indexInfo, min, max);
			return;
		}
		
		long start = min;
		List<T> list = null;
		List<IndexQuery> queries = null;
		while (start <= max) {
			log.info("insert to es data indexName={}. {}/{}/{}", indexInfo.getIndexName(), min, start, max);
			
			//获取列表
			list = getDataListFromDB(start, start + getSize(), param);
			
			//开始id自加
			start = start + getSize();
			
			if (CollectionUtils.isEmpty(list)) {
				//为空，则下一轮
				continue;
			}
			
			//组装成索引对象
			queries = getIndexQueryDataListByDataList(list, newIndexName, indexInfo.getType());
			
			if (CollectionUtils.isEmpty(queries)) {
				continue;
			}

			//批量插入到新索引
			elasticsearchTemplate.bulkIndex(queries);
		}
	}

}
