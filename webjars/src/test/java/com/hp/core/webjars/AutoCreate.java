/**
 * 
 */
package com.hp.core.webjars;

import com.hp.core.mybatis.autocreate.CreateFile;

/**
 * @author huangping
 * 2018年5月31日
 */
public class AutoCreate {

	
	public static void main(String[] args) {
		String tableName = "sys_menu,sys_user,sys_role,sys_role_menu,sys_user_role";
		String mainPathDir = "../";//文件生成的主路径
		String javaDir = "src/main/java";//java文件存放主目录
		String serviceMavenModule = "webjars";//service所在项目名称
		String controllerMavenModule = "webjars";//controller所在项目名称
		String projectPackage = "com.hp.core.webjars";//DAO生成的包地址
		String mappingDir = "src/main/resources/META-INF/mybatis";
		String isCreateJSP = "0"; //是否生成jsp
		String jspFilePath = "src/main/webapp/jsp/newfile";
		String[] params = {tableName, mainPathDir, javaDir, projectPackage, serviceMavenModule, controllerMavenModule, mappingDir, isCreateJSP, jspFilePath};
		CreateFile.main(params);
	}
}
