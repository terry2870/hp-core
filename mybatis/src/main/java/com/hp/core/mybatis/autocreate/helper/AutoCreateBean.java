/**
 * 
 */
package com.hp.core.mybatis.autocreate.helper;

import java.util.List;

import com.hp.core.common.beans.BaseBean;


/**
 * @author huangping
 * 2018年9月19日
 */
public class AutoCreateBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054161024845693362L;

	private List<String> tableNameList;
	String mainPathDir = "../";//文件生成的主路径
    String javaDir = "src/main/java";//java文件存放主目录
    String serviceMavenModule = "none";//service所在项目名称
    String controllerMavenModule = "none";//controller所在项目名称
    String projectPackage = "com.yoho.none";//DAO生成的包地址
    String mappingDir = "src/main/resources/META-INF/mybatis";
	public List<String> getTableNameList() {
		return tableNameList;
	}
	public void setTableNameList(List<String> tableNameList) {
		this.tableNameList = tableNameList;
	}
	public String getMainPathDir() {
		return mainPathDir;
	}
	public void setMainPathDir(String mainPathDir) {
		this.mainPathDir = mainPathDir;
	}
	public String getJavaDir() {
		return javaDir;
	}
	public void setJavaDir(String javaDir) {
		this.javaDir = javaDir;
	}
	public String getServiceMavenModule() {
		return serviceMavenModule;
	}
	public void setServiceMavenModule(String serviceMavenModule) {
		this.serviceMavenModule = serviceMavenModule;
	}
	public String getControllerMavenModule() {
		return controllerMavenModule;
	}
	public void setControllerMavenModule(String controllerMavenModule) {
		this.controllerMavenModule = controllerMavenModule;
	}
	public String getProjectPackage() {
		return projectPackage;
	}
	public void setProjectPackage(String projectPackage) {
		this.projectPackage = projectPackage;
	}
	public String getMappingDir() {
		return mappingDir;
	}
	public void setMappingDir(String mappingDir) {
		this.mappingDir = mappingDir;
	}
    
}
