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
import com.hp.core.webjars.convert.SysRoleConvert;
import com.hp.core.webjars.dal.ISysRoleDAO;
import com.hp.core.webjars.dal.model.SysRole;
import com.hp.core.webjars.model.request.SysRoleRequestBO;
import com.hp.core.webjars.model.response.SysRoleResponseBO;
import com.hp.core.webjars.service.ISysRoleService;

/**
 * 系统角色表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	private static Logger log = LoggerFactory.getLogger(SysRoleServiceImpl.class);

	@Autowired
	private ISysRoleDAO sysRoleDAO;

	@Override
	public void saveSysRole(SysRoleRequestBO request) {
		log.info("saveSysRole with request={}", request);
		SysRole dal = SysRoleConvert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			sysRoleDAO.insertSelective(dal);
		} else {
			//修改
			sysRoleDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysRole success with request={}", request);
	}

	@Override
	public PageResponse<SysRoleResponseBO> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest) {
		log.info("querySysRolePageList with request={}", request);
		SysRole dal = SysRoleConvert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		//查询总数
		int total = sysRoleDAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("querySysRolePageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysRoleResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		//查询列表
		List<SysRole> list = sysRoleDAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysRolePageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysRoleResponseBO> respList = new ArrayList<>();
		for (SysRole a : list) {
			respList.add(SysRoleConvert.dal2BOResponse(a));
		}
		log.info("querySysRolePageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysRole(Integer id) {
		log.info("deleteSysRole with id={}", id);
		sysRoleDAO.deleteByPrimaryKey(id);
		log.info("deleteSysRole success with id={}", id);
	}

	@Override
	public SysRoleResponseBO querySysRoleById(Integer id) {
		log.info("querySysRoleById with id={}", id);
		SysRole dal = sysRoleDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysRoleById error. with result is null. with id={}", id);
			return null;
		}
		return SysRoleConvert.dal2BOResponse(dal);
	}
}
