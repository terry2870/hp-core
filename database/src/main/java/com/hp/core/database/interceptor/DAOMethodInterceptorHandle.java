/**
 * 
 */
package com.hp.core.database.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.hp.core.common.threadprofile.ThreadProfile;
import com.hp.core.database.bean.DAOInterfaceInfoBean;
import com.hp.core.database.bean.DAOInterfaceInfoBean.DBDelayInfo;

/**
 * @author huangping 2018年4月11日
 */
public class DAOMethodInterceptorHandle {

	private static Logger log = LoggerFactory.getLogger(DAOMethodInterceptorHandle.class);
	
	private static ThreadLocal<DAOInterfaceInfoBean> routeKey = new ThreadLocal<>();
	
	//最大数据库查询时间（超过这个时间，就会打印一个告警日志）
	private static final long MAX_DB_DELAY_TIME = 150;
	
	/**
	 * 获取当前线程的数据源路由的key
	 */
	public static DAOInterfaceInfoBean getRouteDAOInfo() {
		return routeKey.get();
	}
	
	public void before(JoinPoint join) {
		log.debug("start before");
		
		DAOInterfaceInfoBean bean = new DAOInterfaceInfoBean();
		Class<?> clazz = join.getThis().getClass();
		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(clazz, clazz.getClassLoader());
		Class<?>[] parentClass = targetInterfaces[0].getInterfaces();
		if (ArrayUtils.isNotEmpty(parentClass)) {
			bean.setParentClassName(parentClass[0]);
		}
		
		bean.setClassName(targetInterfaces[0]);
		bean.setMapperNamespace(targetInterfaces[0].getName());
		MethodSignature signature = (MethodSignature) join.getSignature();
		bean.setMethod(signature.getMethod());
		bean.setStatementId(join.getSignature().getName());
		bean.setParameters(join.getArgs());
		this.setRouteDAOInfo(bean);
		
		entry();
	}
	
	public void throwing(JoinPoint join, Exception ex) {
		log.debug("start throwing");
		log.error("execute db error. with exceptions is: ", ex);
	}

	public void after(JoinPoint point) {
		log.debug("start after");
		exit();
		//释放当前线程的数据
		this.removeRouteDAOInfo();
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
