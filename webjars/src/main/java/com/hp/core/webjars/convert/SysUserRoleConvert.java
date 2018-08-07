package com.hp.core.webjars.convert;

import org.springframework.beans.BeanUtils;

import com.hp.core.webjars.dal.model.SysUserRole;
import com.hp.core.webjars.model.request.SysUserRoleRequestBO;
import com.hp.core.webjars.model.response.SysUserRoleResponseBO;

/**
 * 对象转换类
 * @author huangping
 * 2018-08-06
 */

public class SysUserRoleConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysUserRole boRequest2Dal(SysUserRoleRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysUserRole dal = new SysUserRole();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysUserRoleResponseBO dal2BOResponse(SysUserRole dal) {
		if (dal == null) {
			return null;
		}
		SysUserRoleResponseBO bo = new SysUserRoleResponseBO();
		BeanUtils.copyProperties(dal, bo);
		return bo;
	}
}
