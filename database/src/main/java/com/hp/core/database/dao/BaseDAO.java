/**
 * 
 */
package com.hp.core.database.dao;

/**
 * 统一增删改查的父类接口
 * 继承该接口，可以实现最基本的增删改查操作
 * @author huangping 2018年1月26日
 */
public interface BaseDAO<T> 
	extends
	BaseSelectDAO<T>,
	BaseUpdateDAO<T>,
	BaseInsertDAO<T>,
	BaseDeleteDAO<T> {

}
