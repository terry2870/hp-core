/**
 * 
 */
package com.hp.core.database.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.core.common.threadprofile.ThreadProfile;
import com.hp.core.database.bean.DAOInterfaceInfoBean;
import com.hp.core.database.bean.DAOInterfaceInfoBean.DBDelayInfo;

/**
 * @author huangping 2018年4月11日
 */
public abstract class DAOMethodInterceptorHandle implements MethodInterceptor {

	private static Logger log = LoggerFactory.getLogger(DAOMethodInterceptorHandle.class);
	
	private static ThreadLocal<DAOInterfaceInfoBean> routeKey = new ThreadLocal<>();
	
	//最大数据库查询时间（超过这个时间，就会打印一个告警日志）
	private static final long MAX_DB_DELAY_TIME = 150;
	
	/**
	 * 获取dao操作的对象，方法等
	 * @param invocation
	 * @return
	 */
	public abstract DAOInterfaceInfoBean getDAOInterfaceInfoBean(MethodInvocation invocation);
	
	/**
	 * 获取当前线程的数据源路由的key
	 */
	public static DAOInterfaceInfoBean getRouteDAOInfo() {
		return routeKey.get();
	}
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//获取dao的操作方法，参数等信息
		this.setRouteDAOInfo(getDAOInterfaceInfoBean(invocation));
		
		//设置进入查询
		entry();
		Object obj = null;
		try {
			//执行实际方法
			obj = invocation.proceed();
			return obj;
		} catch (Exception e) {
			throw  e;
		} finally {
			//退出查询
			exit();
			
			//释放当前线程的数据
			this.removeRouteDAOInfo();
		}
	}
	
	/**
	 * 进入查询
	 */
	private void entry() {
		DAOInterfaceInfoBean bean = getRouteDAOInfo();
		ThreadProfile.enter(bean.getMapperNamespace(), bean.getStatementId());
		DBDelayInfo delay = bean.new DBDelayInfo();
		delay.setBeginTime(System.currentTimeMillis());
		bean.setDelay(delay);
	}
	
	/**
	 * 结束查询
	 */
	private void exit() {
		DAOInterfaceInfoBean bean = getRouteDAOInfo();
		DBDelayInfo delay = bean.getDelay();
		delay.setEndTime(System.currentTimeMillis());
		ThreadProfile.exit();
		//输入调用数据库的时间
		if (delay.getEndTime() - delay.getBeginTime() >= MAX_DB_DELAY_TIME) {
			log.warn("execute db expire time. {}", delay);
		}
		
	}
	
	/**
	 * 绑定当前线程数据源路由的key 使用完成后必须调用removeRouteKey()方法删除
	 */
	private void setRouteDAOInfo(DAOInterfaceInfoBean key) {
		routeKey.set(key);
	}

	/**
	 * 删除与当前线程绑定的数据源路由的key
	 */
	private void removeRouteDAOInfo() {
		routeKey.remove();
	}
}
