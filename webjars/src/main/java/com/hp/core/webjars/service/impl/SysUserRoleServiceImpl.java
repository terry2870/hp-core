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

import com.hp.core.webjars.dal.ISysUserRoleDAO;
import com.hp.core.webjars.dal.model.SysUserRole;
import com.hp.core.webjars.service.ISysUserRoleService;

/**
 * @author huangping
 * 2016年8月28日 下午4:24:44
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

	static Logger log = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);
	
	@Autowired
	private ISysUserRoleDAO sysUserRoleDAO;
	
	@Override
	public List<Integer> selectRoleByUserId(Integer userId) {
		log.info("selectRoleByUserId with userId={}", userId);
		List<Integer> list = sysUserRoleDAO.selectRoleByUserId(userId);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("selectRoleByUserId error, result is empty. with userId={}", userId);
			return null;
		}
		log.info("selectRoleByUserId with userId={}", userId);
		return list;
	}

	@Override
	public void insertUserRole(Integer userId, String roleIds) {
		log.info("insertUserRole with userId={}, roleIds={}", userId, roleIds);
		//首先删除
		sysUserRoleDAO.deleteByUserId(userId);
		
		if (StringUtils.isNotEmpty(roleIds)) {
			String[] arr = roleIds.split(",");
			List<SysUserRole> list = new ArrayList<>(arr.length);
			for (String roleId : arr) {
				list.add(new SysUserRole(userId, Integer.parseInt(roleId)));
			}
			sysUserRoleDAO.insertBatch(list);
		}
	}

}
