/**
 * 
 */
package com.hp.core.elasticsearch.search;

import java.util.List;

import org.elasticsearch.search.suggest.Suggest;

import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageRequest;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.bean.request.BaseSearchRequest;

/**
 * 搜索接口
 * @author huangping
 * Mar 18, 2019
 * @param <REQUEST>		请求的对象
 * @param <RESPONSE>	返回的对象
 */
public interface IESSearchService<REQUEST extends BaseSearchRequest, RESPONSE> {

	/**
	 * 获取索引信息
	 * @return
	 */
	public IndexInfo getIndexInfo();
	
	/**
	 * 搜索
	 * @param request
	 * @param page
	 * @param sortList
	 * @return
	 */
	public PageResponse<RESPONSE> search(REQUEST request, PageRequest page, List<OrderBy> sortList);

	/**
	 * 统计数量
	 * @param request
	 * @return
	 */
	public long count(REQUEST request);

	/**
	 * 获取查询建议
	 * @param request
	 * @return
	 */
	public Suggest suggest(REQUEST request);
}
