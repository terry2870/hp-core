/**
 * 
 */
package com.hp.core.jdbc.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.hp.core.database.bean.DAOInterfaceInfoBean;
import com.hp.core.database.interceptor.DAOMethodInterceptorHandle;

/**
 * @author huangping
 * Jul 14, 2020
 */
@Component("DAOMethodInterceptorHandle")
public class JDBCDAOMethodInterceptorHandle extends DAOMethodInterceptorHandle {

	@Override
	public DAOInterfaceInfoBean getDAOInterfaceInfoBean(MethodInvocation invocation) {
		DAOInterfaceInfoBean bean = new DAOInterfaceInfoBean();
		Class<?> clazz = invocation.getThis().getClass();
		Class<?>[] targetInterfaces = ClassUtils.getAllInterfacesForClass(clazz, clazz.getClassLoader());
		
		bean.setClassName(targetInterfaces[0]);
		bean.setMapperNamespace(targetInterfaces[0].getName());
		bean.setMethod(invocation.getMethod());
		Class<?>[] parentClass = targetInterfaces[0].getInterfaces();
		if (ArrayUtils.isNotEmpty(parentClass)) {
			bean.setParentClassName(parentClass[0]);
		}
		bean.setParameters(invocation.getArguments());
		bean.setStatementId(bean.getMethod().getName());
		return bean;
	}

}
