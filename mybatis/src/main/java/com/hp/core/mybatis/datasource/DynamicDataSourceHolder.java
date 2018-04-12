/**
 * 
 */
package com.hp.core.mybatis.datasource;

import com.hp.core.mybatis.bean.DAOInterfaceInfoBean;

/**
 * @author huangping 2018年4月11日
 */
public class DynamicDataSourceHolder {

	private static ThreadLocal<DAOInterfaceInfoBean> routeKey = new ThreadLocal<>();

	/**
	 * 获取当前线程的数据源路由的key
	 */
	public static DAOInterfaceInfoBean getRouteDAOInfo() {
		return routeKey.get();
	}

	/**
	 * 绑定当前线程数据源路由的key 使用完成后必须调用removeRouteKey()方法删除
	 */
	public static void setRouteDAOInfo(DAOInterfaceInfoBean key) {
		routeKey.set(key);
	}

	/**
	 * 删除与当前线程绑定的数据源路由的key
	 */
	public static void removeRouteDAOInfo() {
		routeKey.remove();
	}

}
