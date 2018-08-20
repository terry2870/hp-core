package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.core.common.utils.DateUtil;
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
			dal.setCreateTime(DateUtil.getCurrentTimeSeconds());
			dal.setUpdateTime(dal.getCreateTime());
			dal.setCreateUserId(SessionUtil.getSessionUser().getId());
			sysMenuDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysMenuDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysMenu success with request={}", request);
	}

	@Override
	public void deleteSysMenu(Integer id) {
		log.info("deleteSysMenu with id={}", id);
		sysMenuDAO.deleteByPrimaryKey(id);
		log.info("deleteSysMenu success with id={}", id);
	}

	@Override
	public List<SysMenuResponseBO> queryAllSysMenu() {
		log.info("queryAllSysMenu start");
		SysUserResponseBO user = SessionUtil.getSessionUser();
		List<SysMenu> list = null;
		if (SessionUtil.isSuperUser()) {
			//超级管理员，直接看到所有菜单
			list = sysMenuDAO.selectMenuForSuperAdmin();
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
