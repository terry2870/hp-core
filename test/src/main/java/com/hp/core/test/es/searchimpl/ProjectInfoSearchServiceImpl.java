/**
 * 
 */
package com.hp.core.test.es.searchimpl;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.hp.core.common.utils.NumberUtil;
import com.hp.core.elasticsearch.search.impl.AbstGeneralSearchServiceImpl;
import com.hp.core.test.es.model.request.ProjectSearchRequest;
import com.hp.core.test.es.model.response.ProjectResponse;
 
/**
 * @author huangping
 * Jun 22, 2020
 */
@Service
public class ProjectInfoSearchServiceImpl extends AbstGeneralSearchServiceImpl<ProjectSearchRequest, ProjectResponse> {

	@Override
	public void addQuery(ProjectSearchRequest request, BoolQueryBuilder query, BoolQueryBuilder filter, NativeSearchQueryBuilder searchQuery) {
		if (NumberUtil.isNotEmpty(request.getId())) {
			filter.must(QueryBuilders.termQuery("id", request.getId()));
		}
		
		if (StringUtils.isNotEmpty(request.getProjectName())) {
			query.must(QueryBuilders.matchQuery("projectName", request.getProjectName()));
		}
	}

}
