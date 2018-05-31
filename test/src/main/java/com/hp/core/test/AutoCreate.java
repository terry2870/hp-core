/**
 * 
 */
package com.hp.core.test;

import com.hp.core.mybatis.autocreate.CreateFile;

/**
 * @author huangping
 * 2018年5月31日
 */
public class AutoCreate {

	
	public static void main(String[] args) {
		String tableName = "project_info";
		String mainPathDir = "./";//文件生成的主路径
		String javaDir = "src/main/java";//java文件存放主目录
		String daoPackage = "com.hp.core.test.dal";//DAO生成的包地址
		String modelPackage = daoPackage + ".model";//model生成的包地址
		String mappingDir = "src/main/resources/META-INF/mybatis";
		String[] params = {tableName, mainPathDir, javaDir, daoPackage, modelPackage, mappingDir};
		CreateFile.main(params);
	}
}
