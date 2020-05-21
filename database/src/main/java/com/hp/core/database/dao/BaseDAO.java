/**
 * 
 */
package com.hp.core.database.dao;

/**
 * 统一增删改查的父类接口
 * 继承该接口，可以实现最基本的增删改查操作
 * @author huangping 2018年1月26日
 */
public interface BaseDAO<T, PK> 
	extends
	IBaseSelectDAO<T, PK>,
	IBaseUpdateDAO<T, PK>,
	IBaseInsertDAO<T, PK>,
	IBaseDeleteDAO<T, PK> {
}
