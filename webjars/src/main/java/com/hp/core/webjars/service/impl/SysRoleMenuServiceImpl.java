/**
 * 
 */
package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.core.webjars.dal.ISysRoleMenuDAO;
import com.hp.core.webjars.dal.model.SysRoleMenu;
import com.hp.core.webjars.service.ISysRoleMenuService;

/**
 * @author huangping
 * 2016年9月11日 下午9:31:03
 */
@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {

	static Logger log = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);
	
	@Autowired
	private ISysRoleMenuDAO sysRoleMenuDAO;
	
	@Override
	public List<Integer> selectMenuByRoleId(Integer roleId) {
		log.info("selectMenuByRoleId with roleId={}", roleId);
		List<Integer> list = sysRoleMenuDAO.selectMenuByRoleId(roleId);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("selectMenuByRoleId result is empty. with roleId={}", roleId);
			return null;
		}
		
		return list;
	}

//	@Override
//	public List<SysRoleMenuResponseBO> selectByMenuId(Integer menuId) throws Exception {
//		log.info("selectByMenuId with menuId={}", menuId);
//		List<SysRoleMenu> list = sysRoleMenuDAO.selectByMenuId(menuId);
//		List<SysRoleMenuResponseBO> respList = new ArrayList<>(list.size());
//		for (SysRoleMenu roleMenu : list) {
//			respList.add(SysRoleMenuConvert.db2BOResponse(roleMenu));
//		}
//		return respList;
//	}
//
	@Override
	public void saveSysRoleMenu(Integer roleId, String menuIds) {
		log.info("saveSysRoleMenu with roleId={}, menuIds={}", roleId, menuIds);
		//删除以前的菜单
		sysRoleMenuDAO.deleteByRoleId(roleId);
		
		//插入新菜单
		if (StringUtils.isEmpty(menuIds)) {
			log.warn("saveSysRoleMenu with new menuId is empty.");
			return;
		}
		String[] arr = menuIds.split(",");
		List<SysRoleMenu> list = new ArrayList<>(arr.length);
		for (String menuId : arr) {
			list.add(new SysRoleMenu(roleId, Integer.parseInt(menuId)));
		}
		sysRoleMenuDAO.insertBatch(list);
	}

}
