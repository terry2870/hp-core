package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.core.common.beans.page.PageModel;
import com.hp.core.common.beans.page.PageRequest;
import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.common.enums.StatusEnum;
import com.hp.core.webjars.convert.SysMenuConvert;
import com.hp.core.webjars.dal.ISysMenuDAO;
import com.hp.core.webjars.dal.model.SysMenu;
import com.hp.core.webjars.model.request.SysMenuRequestBO;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;
import com.hp.core.webjars.service.ISysMenuService;
import com.hp.core.webjars.utils.SessionUtil;

/**
 * 系统菜单表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	private static Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);

	@Autowired
	private ISysMenuDAO sysMenuDAO;

	@Override
	public void saveSysMenu(SysMenuRequestBO request) {
		log.info("saveSysMenu with request={}", request);
		SysMenu dal = SysMenuConvert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			sysMenuDAO.insertSelective(dal);
		} else {
			//修改
			sysMenuDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysMenu success with request={}", request);
	}

	@Override
	public PageResponse<SysMenuResponseBO> querySysMenuPageList(SysMenuRequestBO request, PageRequest pageRequest) {
		log.info("querySysMenuPageList with request={}", request);
		SysMenu dal = SysMenuConvert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		//查询总数
		int total = sysMenuDAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("querySysMenuPageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysMenuResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		//查询列表
		List<SysMenu> list = sysMenuDAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysMenuPageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysMenuResponseBO> respList = new ArrayList<>();
		for (SysMenu a : list) {
			respList.add(SysMenuConvert.dal2BOResponse(a));
		}
		log.info("querySysMenuPageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysMenu(Integer id) {
		log.info("deleteSysMenu with id={}", id);
		sysMenuDAO.deleteByPrimaryKey(id);
		log.info("deleteSysMenu success with id={}", id);
	}

	@Override
	public SysMenuResponseBO querySysMenuById(Integer id) {
		log.info("querySysMenuById with id={}", id);
		SysMenu dal = sysMenuDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysMenuById error. with result is null. with id={}", id);
			return null;
		}
		return SysMenuConvert.dal2BOResponse(dal);
	}

	@Override
	public List<SysMenuResponseBO> queryAllSysMenu() {
		log.info("queryAllSysMenu start");
		SysUserResponseBO user = SessionUtil.getSessionUser();
		List<SysMenu> list = null;
		if (SessionUtil.isSuperUser()) {
			//超级管理员，直接看到所有菜单
			
			SysMenu menu = new SysMenu();
			menu.setStatus(StatusEnum.OPEN.getValue());
			PageModel page = new PageModel();
			page.setSortColumn("sortNumber");
			list = sysMenuDAO.selectPageListByParams(menu, page);
		} else {
			//其他用户，只能看到分配的菜单
			List<Integer> menuIds = sysMenuDAO.selectByUserId(user.getId());
			if (CollectionUtils.isEmpty(menuIds)) {
				log.warn("queryAllSysMenu error. menu is empty");
				return null;
			}
			list = sysMenuDAO.selectSysMenu(StringUtils.join(menuIds, ","));
		}
		if (CollectionUtils.isEmpty(list)) {
			log.warn("queryAllSysMenu error. with result is empty");
			return null;
		}
		List<SysMenuResponseBO> respList = new ArrayList<>(list.size());
		for (SysMenu menu : list) {
			respList.add(SysMenuConvert.dal2BOResponse(menu));
		}
		return respList;
	}
}
