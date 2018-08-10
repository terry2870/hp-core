/**
 * 
 */
package com.hp.core.webjars.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.hp.core.webjars.constants.BaseConstant;
import com.hp.core.webjars.enums.CodeEnum;
import com.hp.core.webjars.enums.IdentityEnum;
import com.hp.core.webjars.exceptions.NoRightException;
import com.hp.core.webjars.model.OperaBean;
import com.hp.core.webjars.model.response.SysMenuResponseBO;
import com.hp.core.webjars.model.response.SysUserResponseBO;

/**
 * @author huangping 2016年8月21日 下午11:01:25
 */
@Component("UrlInterceptor")
public class UrlInterceptor implements HandlerInterceptor {

	// 保存当前用户的对象
	private static final ThreadLocal<OperaBean> localUser = new ThreadLocal<>();

	// 免过滤列表（不管有没有session都可以访问）
	@Value("#{'${hp.core.firstNoFilterList:}'.split(',')}")
	private List<String> firstNoFilterList;
		
	//默认的第一免过滤
	private static final List<String> defaultFirstNoFilterList = Lists.newArrayList("/doLogin", "/login", "/logout", "/refeshCheckCode", "/forward");

	// 第二级免过滤列表（只要有session就都可以访问）
	@Value("#{'${hp.core.secondNoFilterList:}'.split(',')}")
	private List<String> secondNoFilterList;
	
	//默认的第二免过滤
	private static final List<String> defaultSecondNoFilterList = Lists.newArrayList("");

	// 超级管理员账号
	private List<String> superManagerList;
	
	//默认的超级管理员
	private static final List<String> defaultSuperManagerList = Lists.newArrayList("admin");

	private static Logger log = LoggerFactory.getLogger(UrlInterceptor.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String url = (String) request.getAttribute("javax.servlet.include.request_uri");
		OperaBean opera = new OperaBean();
		opera.setUserIp(request.getRemoteAddr());
		localUser.set(opera);
		if (StringUtils.isEmpty(url)) {
			url = request.getRequestURI();
		}
		// 第一层免过滤
		if (StringUtils.isEmpty(url) || contains(defaultFirstNoFilterList, url) || contains(firstNoFilterList, url)) {
			return true;
		}
		// 过滤session失效的
		SysUserResponseBO user = (SysUserResponseBO) request.getSession().getAttribute(BaseConstant.USER_SESSION);
		if (user == null) {
			log.warn("访问url={}， session 过期，重新登录", url);
			response.sendRedirect(request.getContextPath() + "/logout");
			return false;
		}
		opera.setSuperUser(IdentityEnum.checkIsSuperUser(user.getIdentity()));
		opera.setManager(IdentityEnum.checkIsManager(user.getIdentity()));
		opera.setNormalUser(IdentityEnum.checkIsNormalUser(user.getIdentity()));
		
		opera.setUser(user);
		

		// 第二层免过滤
		if (contains(defaultSecondNoFilterList, url) || contains(secondNoFilterList, url)) {
			return true;
		}
		// 超级管理员
		if (defaultSuperManagerList.contains(user.getLoginName()) || (CollectionUtils.isNotEmpty(superManagerList) && superManagerList.contains(user.getLoginName()))) {
			return true;
		}
		
		//按照权限过滤
		String[] arr = null;
		List<SysMenuResponseBO> userMenu = (List<SysMenuResponseBO>) request.getSession().getAttribute(BaseConstant.USER_MENU);
		for (SysMenuResponseBO bo : userMenu) {
			if (StringUtils.isNotEmpty(bo.getMenuUrl()) && bo.getMenuUrl().equals(url)) {
				return true;
			}
			if (StringUtils.isNotEmpty(bo.getExtraUrl())) {
				arr = bo.getExtraUrl().split(",");
				for (String s : arr) {
					if (s.equals(url)) {
						return true;
					}
				}
			}
		}
		log.warn("你没有权限访问【"+ url +"】");
		throw new NoRightException(CodeEnum.NO_RIGHT.getCode(), "您没有权限访问：【"+ url +"】");
	}

	/**
	 * 获取当前操作者信息
	 * @return
	 */
    public static OperaBean getOperater(){
        return localUser.get();
    }
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		localUser.remove();
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 是否包含url
	 * @param list
	 * @param url
	 * @return
	 */
	private static boolean contains(List<String> list, String url) {
		if (CollectionUtils.isEmpty(list) || StringUtils.isEmpty(url)) {
			return false;
		}
		for (String str : list) {
			if (StringUtils.isEmpty(str)) {
				continue;
			}
			if (url.equals(str) || url.startsWith(str)) {
				return true;
			}
		}
		return false;
	}


}
