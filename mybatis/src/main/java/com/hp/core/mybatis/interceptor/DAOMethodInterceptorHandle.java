/**
 * 
 */
package com.hp.core.mybatis.interceptor;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import com.hp.core.mybatis.bean.DAOInterfaceInfoBean;
import com.hp.core.mybatis.datasource.DynamicDataSourceHolder;

/**
 * @author huangping 2018年4月11日
 */
public class DAOMethodInterceptorHandle {

	private static Logger log = LoggerFactory.getLogger(DAOMethodInterceptorHandle.class);
	
	public void before(JoinPoint join) {
		log.debug("start before");
		
		DAOInterfaceInfoBean bean = new DAOInterfaceInfoBean();
		Class<?> clazz = join.getThis().getClass();
		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(clazz, clazz.getClassLoader());
		bean.setMapperNamespace(targetInterfaces[0].getName());
		bean.setStatementId(join.getSignature().getName());
		DynamicDataSourceHolder.setRouteDAOInfo(bean);
	}

	public void after(JoinPoint point) {
		log.debug("start after");
		//释放当前线程的数据
		DynamicDataSourceHolder.removeRouteDAOInfo();
	}
}
