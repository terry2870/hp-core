/**
 * 
 */
package com.hp.core.webjars.model;

import com.hp.core.common.beans.BaseBean;
import com.hp.core.webjars.model.response.SysUserResponseBO;

/**
 * @author huangping
 * 2016年8月23日 下午11:31:24
 */
public class OperaBean extends BaseBean {

	private static final long serialVersionUID = -5811301321042214170L;
	
	private String userIp;
	private SysUserResponseBO user;
	private int operaType;
	private String logInfo;
	
	// 超级管理员
	private boolean superUser;
	//店长
	private boolean manager;
	//普通员工
	private boolean normalUser;
	
	public int getOperaType() {
		return operaType;
	}
	public void setOperaType(int operaType) {
		this.operaType = operaType;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public SysUserResponseBO getUser() {
		return user;
	}
	public void setUser(SysUserResponseBO user) {
		this.user = user;
	}

	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	public boolean isSuperUser() {
		return superUser;
	}
	public void setSuperUser(boolean superUser) {
		this.superUser = superUser;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public boolean isNormalUser() {
		return normalUser;
	}
	public void setNormalUser(boolean normalUser) {
		this.normalUser = normalUser;
	}

}
