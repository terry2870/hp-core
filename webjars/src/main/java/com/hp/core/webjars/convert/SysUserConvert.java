package com.hp.core.webjars.convert;

import org.springframework.beans.BeanUtils;

import com.hp.core.webjars.dal.model.SysUser;
import com.hp.core.webjars.enums.IdentityEnum;
import com.hp.core.webjars.model.request.SysUserRequestBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;

/**
 * 对象转换类
 * @author huangping
 * 2018-08-06
 */

public class SysUserConvert {

	/**
	 * bo request --> dal
	 * @param bo
	 * @return
	 */
	public static SysUser boRequest2Dal(SysUserRequestBO bo) {
		if (bo == null) {
			return null;
		}
		SysUser dal = new SysUser();
		BeanUtils.copyProperties(bo, dal);
		return dal;
	}

	/**
	 * dal --> bo response
	 * @param dal
	 * @return
	 */
	public static SysUserResponseBO dal2BOResponse(SysUser dal) {
		if (dal == null) {
			return null;
		}
		SysUserResponseBO bo = new SysUserResponseBO();
		BeanUtils.copyProperties(dal, bo);
		bo.setIdentityStr(IdentityEnum.getNameByValue(bo.getIdentity()));
		return bo;
	}
}
