/**
 * 
 */
package com.hp.core.elasticsearch.search.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.data.elasticsearch.core.query.SourceFilter;

import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.common.constants.ElasticsearchConstant;
import com.hp.core.database.bean.OrderBy;
import com.hp.core.database.bean.PageRequest;
import com.hp.core.elasticsearch.bean.IndexInfo;
import com.hp.core.elasticsearch.bean.request.BaseSearchRequest;
import com.hp.core.elasticsearch.factory.IndexInfoFactory;
import com.hp.core.elasticsearch.search.IESSearchService;

/**
 * 一般搜索接口
 * @author huangping
 * Mar 18, 2019
 * @param <REQUEST>		请求的对象
 * @param <RESPONSE>	返回的对象
 */
public abstract class AbstGeneralSearchServiceImpl<REQUEST extends BaseSearchRequest, RESPONSE> implements IESSearchService<REQUEST, RESPONSE> {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	/**
	 * 添加另外的查询条件
	 * @param request
	 * @param query
	 * @param filter
	 * @param searchQuery
	 */
	public abstract void addQuery(REQUEST request, BoolQueryBuilder query, BoolQueryBuilder filter, NativeSearchQueryBuilder searchQuery);

	/**
	 * 建议纠错
	 * @param request
	 * @return
	 */
	public SuggestBuilder withSuggest(REQUEST request){
		return null;
	}

	/**
	 * 获取返回对象的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<RESPONSE> getResponseClass(){
		return (Class <RESPONSE>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	/**
	 * 检查输入参数
	 * @param request
	 * @param page
	 * @param sortList
	 * @return
	 */
	public boolean checkRequest(REQUEST request, PageRequest page, List<OrderBy> sortList) {
		return true;
	}
	
	/**
	 * 设置脚本查询
	 * @param request
	 * @return
	 */
	public ScriptField withScriptField(REQUEST request) {
		return null;
	}
	
	/**
	 * 设置返回字段
	 * @param request
	 * @return
	 */
	public SourceFilter withSourceFilter(REQUEST request) {
		return new FetchSourceFilterBuilder()
				.withIncludes(StringUtils.isEmpty(request.getReturnFields()) ? new String[] {"*"} : StringUtils.split(request.getReturnFields(), ","))
				.build();
	}
	
	/**
	 * 处理返回的list
	 * 当查询出结果后执行
	 * @param request
	 * @param list
	 */
	public void dealResponseList(REQUEST request, List<RESPONSE> list) {
	}
	
	@Override
	public IndexInfo getIndexInfo() {
		return IndexInfoFactory.getInstance().getIndexInfoByActualTypeArguments(this.getClass());
	}

	protected NativeSearchQueryBuilder searchQueryBuilder(REQUEST request, PageRequest page, List<OrderBy> sortList) {
		if (!checkRequest(request, page, sortList)) {
			//查询条件检查失败
			return null;
		}

		IndexInfo indexInfo = getIndexInfo();

		NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder()
				.withIndices(indexInfo.getAlias())
				.withTypes(indexInfo.getType());

		//查询条件
		withQuery(request, searchQuery);

		return searchQuery;
	}

	/**
	 * 搜索
	 * @param request
	 * @param page
	 * @param sortList
	 * @return
	 */
	@Override
	public PageResponse<RESPONSE> search(REQUEST request, PageRequest page, List<OrderBy> sortList) {
		PageResponse<RESPONSE> resp = new PageResponse<>(page.getPage(), page.getLimit());
		NativeSearchQueryBuilder searchQuery = searchQueryBuilder(request, page, sortList);
		if(searchQuery == null){
			return resp;
		}

		//分页
		withPage(searchQuery, page);
		
		//排序
		withSort(request, searchQuery, sortList);
		
		//查询
		Page<RESPONSE> result = elasticsearchTemplate.queryForPage(searchQuery.build(), getResponseClass());
		if (result == null) {
			return resp;
		}
		
		//总数
		resp.setTotal((int) result.getTotalElements());
		
		//列表
		List<RESPONSE> list = result.getContent();
		dealResponseList(request, list);
		resp.setRows(list);
		return resp;
	}

	@Override
	public long count(REQUEST request) {
		NativeSearchQueryBuilder searchQuery = searchQueryBuilder(request, null, null);
		if(searchQuery == null){
			return 0;
		}

		return elasticsearchTemplate.count(searchQuery.build());
	}

	@Override
	public Suggest suggest(REQUEST request) {
		SuggestBuilder suggestBuilder = suggestBuilder(request);

		if(suggestBuilder == null){
			return null;
		}

		IndexInfo indexInfo = getIndexInfo();

		SearchResponse searchResponse = elasticsearchTemplate.suggest(suggestBuilder, indexInfo.getAlias());
		if(searchResponse == null || !RestStatus.OK.equals(searchResponse.status())){
			return null;
		}

		return searchResponse.getSuggest();
	}

	/**
	 * 建议纠错
	 * @param request
	 * @return
	 */
	protected SuggestBuilder suggestBuilder(REQUEST request){
		if (!checkRequest(request, null, null)) {
			//查询条件检查失败
			return null;
		}

		return withSuggest(request);
	}

	/**
	 * 设置查询条件
	 * @param request
	 * @param searchQuery
	 */
	protected void withQuery(REQUEST request, NativeSearchQueryBuilder searchQuery) {
		BoolQueryBuilder filter = QueryBuilders.boolQuery();
		BoolQueryBuilder query = QueryBuilders.boolQuery();
		
		searchQuery.withFilter(filter);
		searchQuery.withQuery(query);
		
		addQuery(request, query, filter, searchQuery);
		
		SourceFilter sourceFilter = withSourceFilter(request);
		if (sourceFilter != null) {
			searchQuery.withSourceFilter(sourceFilter);
		}
		
		ScriptField scriptField = withScriptField(request);
		if (scriptField != null) {
			searchQuery.withScriptField(scriptField);
		}
		
	}
	
	/**
	 * 排序
	 * @param request
	 * @param searchQuery
	 * @param sortList
	 */
	protected void withSort(REQUEST request, NativeSearchQueryBuilder searchQuery, List<OrderBy> sortList) {
		if (CollectionUtils.isEmpty(sortList)) {
			searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
			return;
		}
		for (OrderBy orderBy : sortList) {
			if (ElasticsearchConstant.SORT_ORDER_SCORE.equals(orderBy.getFieldName())) {
				//按照分值排序
				searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
			} else {
				searchQuery.withSort(SortBuilders.fieldSort(orderBy.getFieldName())
						.order(SortOrder.fromString(orderBy.getDirection().toString()))
						);
			}
		}
		searchQuery.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
	}
	
	/**
	 * 分页
	 * @param searchQuery
	 * @param page
	 */
	protected void withPage(NativeSearchQueryBuilder searchQuery, PageRequest page) {
		if (page == null) {
			return;
		}
		Pageable p = org.springframework.data.domain.PageRequest.of(page.getPage() - 1, page.getLimit());
		searchQuery.withPageable(p);
	}
}
