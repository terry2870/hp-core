/**
 * 
 */
package com.hp.core.test.es.indeximpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.hp.core.elasticsearch.annotation.SearchIndex;
import com.hp.core.elasticsearch.index.impl.AbstPageLimitIndexServiceImpl;
import com.hp.core.test.dal.model.ProjectInfo;
import com.hp.core.test.es.model.response.ProjectResponse;

/**
 * @author huangping
 * Jun 22, 2020
 */
@Service("projectInfoServiceImpl")
@SearchIndex(rebuildCron = "25 17 2 */1 * ? ")
public class ProjectInfoIndexServiceImpl extends AbstPageLimitIndexServiceImpl<ProjectInfo, ProjectResponse> {

	@Override
	public List<ProjectResponse> getESModelFromDBModel(List<ProjectInfo> list) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		List<ProjectResponse> respList = new ArrayList<>(list.size());
		ProjectResponse bo = null;
		for (ProjectInfo p : list) {
			bo = new ProjectResponse();
			bo.setEnterpriseContacts(p.getEnterpriseContacts());
			bo.setEnterpriseMobile(p.getEnterpriseMobile());
			bo.setEnterpriseName(p.getEnterpriseName());
			bo.setId(p.getId().toString());
			bo.setProjectName(p.getProjectName());
			bo.setProjectType(p.getProjectType());
			respList.add(bo);
		}
		return respList;
	}

	@Override
	public String getId(ProjectResponse e) {
		return e.getId().toString();
	}


}
