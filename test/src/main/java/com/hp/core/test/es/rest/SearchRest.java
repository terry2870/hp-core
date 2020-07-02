/**
 * 
 */
package com.hp.core.test.es.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hp.core.common.beans.Response;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.database.bean.PageRequest;
import com.hp.core.elasticsearch.search.IESSearchService;
import com.hp.core.test.es.model.request.ProjectSearchRequest;
import com.hp.core.test.es.model.response.ProjectResponse;

/**
 * @author huangping
 * Jun 24, 2020
 */
@RestController
@RequestMapping("/search")
public class SearchRest {

	@Autowired
	private IESSearchService<ProjectSearchRequest, ProjectResponse> projectInfoService;
	
	@RequestMapping("searchProjectInfo")
	public Response<PageResponse<ProjectResponse>> searchProject(ProjectSearchRequest request, PageRequest page) {
		PageResponse<ProjectResponse> resp = projectInfoService.search(request, page, null);
		return Response.success(resp);
	}
}
