package com.hp.core.webjars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.core.common.beans.page.PageResponse;
import com.hp.core.common.enums.StatusEnum;
import com.hp.core.common.exceptions.CommonException;
import com.hp.core.common.utils.DateUtil;
import com.hp.core.database.bean.PageModel;
import com.hp.core.database.bean.PageRequest;
import com.hp.core.database.bean.SQLBuilders;
import com.hp.core.database.bean.SQLWhere;
import com.hp.core.webjars.convert.SysRoleConvert;
import com.hp.core.webjars.dal.ISysRoleDAO;
import com.hp.core.webjars.dal.model.SysRole;
import com.hp.core.webjars.model.request.SysRoleRequestBO;
import com.hp.core.webjars.model.response.SysRoleResponseBO;
import com.hp.core.webjars.service.ISysRoleService;
import com.hp.core.webjars.utils.SessionUtil;

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
		
		//检查参数
		saveSysRoleCheck(request);
		
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			dal.setCreateTime(DateUtil.getCurrentTimeSeconds());
			dal.setUpdateTime(dal.getCreateTime());
			dal.setCreateUserId(SessionUtil.getSessionUser().getId());
			sysRoleDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysRoleDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysRole success with request={}", request);
	}

	@Override
	public PageResponse<SysRoleResponseBO> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest) {
		log.info("querySysRolePageList with request={}", request);
		PageModel page = pageRequest.toPageModel();

		List<SQLWhere> whereList = SQLWhere.builder()
				.eq("status", request.getStatus())
				.build();
		//查询总数
		int total = sysRoleDAO.selectCount(whereList);
		if (total == 0) {
			log.warn("querySysRolePageList error. with total=0. with request={}", request);
			return null;
		}
		PageResponse<SysRoleResponseBO> resp = new PageResponse<>();
		resp.setCurrentPage(pageRequest.getPage());
		resp.setPageSize(pageRequest.getRows());
		resp.setTotal(total);

		SQLBuilders builders = SQLBuilders.emptyBuilder()
				.withWhere(whereList)
				.withPage(page)
				;
		//查询列表
		List<SysRole> list = sysRoleDAO.selectList(builders);
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
		SysRole role = new SysRole();
		role.setId(id);
		role.setStatus(StatusEnum.DELETE.getValue());
		sysRoleDAO.updateByPrimaryKeySelective(role);
		
		//sysRoleDAO.deleteByPrimaryKey(id);
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
	
	/**
	 * 保存前的检查参数
	 * @param request
	 */
	public void saveSysRoleCheck(SysRoleRequestBO request) {
		SysRole role = sysRoleDAO.selectOne(SQLBuilders.emptyBuilder()
				.withWhere(SQLWhere.builder()
						.eq("role_name", request.getRoleName())
						.build()
						)
				);
		if (role == null) {
			return;
		}
		
		if (request.getId() == null || request.getId().intValue() == 0) {
			//新增
			log.warn("saveSysRole error. roleName is exists. with request={}", request);
			throw new CommonException(500, "角色名已经存在");
		} else {
			//修改
			if (!role.getId().equals(request.getId())) {
				log.warn("saveSysRole error. roleName is exists. with request={}", request);
				throw new CommonException(500, "角色名已经存在");
			}
		}
	}
}
