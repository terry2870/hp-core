/**
 * 
 */
package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
	public void insertUserRole(Integer userId, List<Integer> roleIdList) {
		log.info("insertUserRole with userId={}, roleIdList={}", userId, roleIdList);
		//首先删除
		sysUserRoleDAO.deleteByUserId(userId);
		
		if (CollectionUtils.isEmpty(roleIdList)) {
			return;
		}
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for (Integer roleId : roleIdList) {
			list.add(new SysUserRole(userId, roleId));
		}
		sysUserRoleDAO.insertBatch(list);
	}

}
