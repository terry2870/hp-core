package com.hp.core.webjars.convert;

import org.springframework.beans.BeanUtils;

import com.hp.core.webjars.dal.model.SysRoleMenu;
import com.hp.core.webjars.model.request.SysRoleMenuRequestBO;
import com.hp.core.webjars.model.response.SysRoleMenuResponseBO;

/**
 * 对象转换类
 * @author huangping
 * 2018-08-06
 */

public class SysRoleMenuConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysRoleMenu boRequest2Dal(SysRoleMenuRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysRoleMenu dal = new SysRoleMenu();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysRoleMenuResponseBO dal2BOResponse(SysRoleMenu dal) {
		if (dal == null) {
			return null;
		}
		SysRoleMenuResponseBO bo = new SysRoleMenuResponseBO();
		BeanUtils.copyProperties(dal, bo);
		return bo;
	}
}
