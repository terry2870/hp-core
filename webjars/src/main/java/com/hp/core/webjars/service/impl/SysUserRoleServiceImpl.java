package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.webjars.convert.SysUserRoleConvert;
import com.hp.core.webjars.dal.ISysUserRoleDAO;
import com.hp.core.webjars.dal.model.SysUserRole;
import com.hp.core.webjars.model.request.SysUserRoleRequestBO;
import com.hp.core.webjars.model.response.SysUserRoleResponseBO;
import com.hp.core.webjars.service.ISysUserRoleService;

/**
 * 用户角色表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

	private static Logger log = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

	@Autowired
	private ISysUserRoleDAO sysUserRoleDAO;

	@Override
	public void saveSysUserRole(SysUserRoleRequestBO request) {
		log.info("saveSysUserRole with request={}", request);
		SysUserRole dal = SysUserRoleConvert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			sysUserRoleDAO.insertSelective(dal);
		} else {
			//修改
			sysUserRoleDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysUserRole success with request={}", request);
	}

	@Override
	public PageResponse<SysUserRoleResponseBO> querySysUserRolePageList(SysUserRoleRequestBO request, PageRequest pageRequest) {
		log.info("querySysUserRolePageList with request={}", request);
		SysUserRole dal = SysUserRoleConvert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		//查询总数
		int total = sysUserRoleDAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("querySysUserRolePageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysUserRoleResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		//查询列表
		List<SysUserRole> list = sysUserRoleDAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysUserRolePageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysUserRoleResponseBO> respList = new ArrayList<>();
		for (SysUserRole a : list) {
			respList.add(SysUserRoleConvert.dal2BOResponse(a));
		}
		log.info("querySysUserRolePageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysUserRole(Integer id) {
		log.info("deleteSysUserRole with id={}", id);
		sysUserRoleDAO.deleteByPrimaryKey(id);
		log.info("deleteSysUserRole success with id={}", id);
	}

	@Override
	public SysUserRoleResponseBO querySysUserRoleById(Integer id) {
		log.info("querySysUserRoleById with id={}", id);
		SysUserRole dal = sysUserRoleDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysUserRoleById error. with result is null. with id={}", id);
			return null;
		}
		return SysUserRoleConvert.dal2BOResponse(dal);
	}
}
