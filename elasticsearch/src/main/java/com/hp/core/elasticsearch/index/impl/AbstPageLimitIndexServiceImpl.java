/**
 * 
 */
package com.hp.core.elasticsearch.index.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.query.IndexQuery;

import com.hp.core.database.bean.PageModel;
import com.hp.core.elasticsearch.bean.IndexInfo;

/**
 * 使用pagel imit分页的方式来获取数据，新建索引
 * @author huangping
 * Apr 8, 2019
 */
public abstract class AbstPageLimitIndexServiceImpl<T, E> extends AbstSimpleIndexServiceImpl<T, E> {

	private static Logger log = LoggerFactory.getLogger(AbstPageLimitIndexServiceImpl.class);
	
	/**
	 * 获取总数
	 * @param param
	 * @return
	 */
	protected int getTotal(T param) {
		return baseMapper.selectCountByParams(param);
	}
	
	/**
	 * 分页查询列表
	 * @param param
	 * @param page
	 * @return
	 */
	protected List<T> getDataListFromDB(T param, PageModel page) {
		return baseMapper.selectPageListByParams(param, page);
	}
	
	@Override
	public void insertIntoES(IndexInfo indexInfo, String newIndexName) {
		T param = getQueryParams();
		int total = getTotal(param);
		if (total == 0) {
			return;
		}
		
		int size = (int) getSize();
		int currentPage = 1;
		//获取总页数
		int totalPage = (int) Math.ceil((double) total / size);
		PageModel page = null;
		List<T> list = null;
		List<IndexQuery> queries = null;
		while (currentPage <= totalPage) {
			log.info("insert to es data indexName={}. {}/{}", indexInfo.getIndexName(), currentPage, totalPage);
			//分页查询数据
			page = PageModel.of(currentPage, size);
			list = getDataListFromDB(param, page);
			
			//页数自加
			currentPage++;
			if (CollectionUtils.isEmpty(list)) {
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
