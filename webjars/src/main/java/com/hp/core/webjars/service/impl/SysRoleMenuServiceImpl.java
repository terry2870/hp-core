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
import com.hp.core.webjars.convert.SysRoleMenuConvert;
import com.hp.core.webjars.dal.ISysRoleMenuDAO;
import com.hp.core.webjars.dal.model.SysRoleMenu;
import com.hp.core.webjars.model.request.SysRoleMenuRequestBO;
import com.hp.core.webjars.model.response.SysRoleMenuResponseBO;
import com.hp.core.webjars.service.ISysRoleMenuService;

/**
 * 系统角色菜单关联表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {

	private static Logger log = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);

	@Autowired
	private ISysRoleMenuDAO sysRoleMenuDAO;

	@Override
	public void saveSysRoleMenu(SysRoleMenuRequestBO request) {
		log.info("saveSysRoleMenu with request={}", request);
		SysRoleMenu dal = SysRoleMenuConvert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			sysRoleMenuDAO.insertSelective(dal);
		} else {
			//修改
			sysRoleMenuDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysRoleMenu success with request={}", request);
	}

	@Override
	public PageResponse<SysRoleMenuResponseBO> querySysRoleMenuPageList(SysRoleMenuRequestBO request, PageRequest pageRequest) {
		log.info("querySysRoleMenuPageList with request={}", request);
		SysRoleMenu dal = SysRoleMenuConvert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		//查询总数
		int total = sysRoleMenuDAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("querySysRoleMenuPageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysRoleMenuResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		//查询列表
		List<SysRoleMenu> list = sysRoleMenuDAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysRoleMenuPageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysRoleMenuResponseBO> respList = new ArrayList<>();
		for (SysRoleMenu a : list) {
			respList.add(SysRoleMenuConvert.dal2BOResponse(a));
		}
		log.info("querySysRoleMenuPageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysRoleMenu(Integer id) {
		log.info("deleteSysRoleMenu with id={}", id);
		sysRoleMenuDAO.deleteByPrimaryKey(id);
		log.info("deleteSysRoleMenu success with id={}", id);
	}

	@Override
	public SysRoleMenuResponseBO querySysRoleMenuById(Integer id) {
		log.info("querySysRoleMenuById with id={}", id);
		SysRoleMenu dal = sysRoleMenuDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysRoleMenuById error. with result is null. with id={}", id);
			return null;
		}
		return SysRoleMenuConvert.dal2BOResponse(dal);
	}
}
