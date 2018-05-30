/**
 * 
 */
package com.hp.core.mybatis.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hp.core.mybatis.provider.BaseSelectProvider;

/**
 * 实现基本的更新操作
 * 继承该接口，可以自动得到基本的更新操作
 * @author huangping
 * 2018年5月29日
 */
public interface BaseDeleteMapper<T> {

	@UpdateProvider(type = BaseSelectProvider.class, method = "selectAllCount")
	public Integer selectAllCount();
}
