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
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.DateUtil;
import com.hp.core.common.utils.MD5Util;
import com.hp.core.webjars.convert.SysUserConvert;
import com.hp.core.webjars.dal.ISysUserDAO;
import com.hp.core.webjars.dal.model.SysUser;
import com.hp.core.webjars.model.request.SysUserRequestBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;
import com.hp.core.webjars.service.ISysUserService;

/**
 * 系统用户表业务操作接口实现
 * @author huangping
 * 2018-08-06
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

	private static Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	@Autowired
	private ISysUserDAO sysUserDAO;

	@Override
	public void saveSysUser(SysUserRequestBO request) {
		log.info("saveSysUser with request={}", request);
		SysUser dal = SysUserConvert.boRequest2Dal(request);
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			sysUserDAO.insertSelective(dal);
		} else {
			//修改
			sysUserDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysUser success with request={}", request);
	}

	@Override
	public PageResponse<SysUserResponseBO> querySysUserPageList(SysUserRequestBO request, PageRequest pageRequest) {
		log.info("querySysUserPageList with request={}", request);
		SysUser dal = SysUserConvert.boRequest2Dal(request);
		PageModel page = pageRequest.toPageModel();

		//查询总数
		int total = sysUserDAO.selectCountByParams(dal);
		if (total == 0) {
			log.warn("querySysUserPageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysUserResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		//查询列表
		List<SysUser> list = sysUserDAO.selectPageListByParams(dal, page);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("querySysUserPageList error. with list is empty. with request={}", request);
			return resp;
		}

		List<SysUserResponseBO> respList = new ArrayList<>();
		for (SysUser a : list) {
			respList.add(SysUserConvert.dal2BOResponse(a));
		}
		log.info("querySysUserPageList success. with request={}", request);
		return new PageResponse<>(total, respList, page.getCurrentPage(), page.getPageSize());
	}

	@Override
	public void deleteSysUser(Integer id) {
		log.info("deleteSysUser with id={}", id);
		sysUserDAO.deleteByPrimaryKey(id);
		log.info("deleteSysUser success with id={}", id);
	}

	@Override
	public SysUserResponseBO querySysUserById(Integer id) {
		log.info("querySysUserById with id={}", id);
		SysUser dal = sysUserDAO.selectByPrimaryKey(id);
		if (dal == null) {
			log.warn("querySysUserById error. with result is null. with id={}", id);
			return null;
		}
		return SysUserConvert.dal2BOResponse(dal);
	}

	@Override
	public SysUserResponseBO login(SysUserRequestBO request) {
		log.info("login start with request={}", request);
		
		if (StringUtils.isEmpty(request.getLoginName())) {
			log.warn("login error with loginName is empty. with request={}", request);
			throw new CommonException(500, "登录名为空");
		}
		if (StringUtils.isEmpty(request.getLoginPwd())) {
			log.warn("login error with loginPwd is empty. with request={}", request);
			throw new CommonException(500, "密码为空");
		}
				
		SysUser user = new SysUser();
		user.setLoginName(request.getLoginName());
		user.setLoginPwd(MD5Util.getMD5(request.getLoginPwd()));
		user.setStatus(StatusEnum.OPEN.getValue());
		List<SysUser> userList = sysUserDAO.selectListByParams(user);
		if (CollectionUtils.isEmpty(userList)) {
			log.warn("login error with login error. with request={}", request);
			throw new CommonException(500, "用户名或密码错误");
		}
		
		//更新最后登录时间
		SysUser u = new SysUser();
		u.setId(userList.get(0).getId());
		u.setLastLoginTime(DateUtil.getCurrentTimeSeconds());
		sysUserDAO.updateByPrimaryKeySelective(u);
		
		SysUserResponseBO response = SysUserConvert.dal2BOResponse(userList.get(0));
		log.info("login success with request={}", request);
		return response;
	}
}
