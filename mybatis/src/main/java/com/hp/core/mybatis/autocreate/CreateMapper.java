/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年5月31日
 */
public class CreateMapper {

	/**
	 * 生成mapper文件
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		String daoPackage = CreateFile.PROJECT_PACKAGE + ".dal";
		String daoModelPackage = CreateFile.PROJECT_PACKAGE + ".dal.model";
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\r\n");
		sb.append("<mapper namespace=\"").append(daoPackage).append(".I").append(table.getModelName()).append("DAO\">\r\n");
		sb.append("	<resultMap id=\"BaseResultMap\" type=\"").append(daoModelPackage).append(".").append(table.getModelName()).append("\">\r\n");
		sb.append("	</resultMap>\r\n");
		sb.append("\r\n");
		sb.append("</mapper>\r\n");
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAL_DIR_NAME +"/" + CreateFile.MAPPING_DIR + "/"+ table.getModelName() +"Mapper.xml";
		CreateFile.saveFile(fileName, sb.toString());
	}
}
