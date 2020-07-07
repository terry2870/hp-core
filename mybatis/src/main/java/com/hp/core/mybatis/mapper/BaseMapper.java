/**
 * 
 */
package com.hp.core.mybatis.mapper;

/**
 * 统一增删改查的父类接口
 * 继承该接口，可以实现最基本的增删改查操作
 * @author huangping 2018年1月26日
 */
public interface BaseMapper<MODEL, PK> 
	extends
	BaseSelectMapper<MODEL, PK>,
	BaseDeleteMapper<MODEL, PK>,
	BaseUpdateMapper<MODEL, PK>,
	BaseInsertMapper<MODEL, PK>
	{
}
