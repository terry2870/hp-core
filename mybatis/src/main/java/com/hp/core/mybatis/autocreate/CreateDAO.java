/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;

/**
 * @author huangping
 * 2018年5月31日
 */
public class CreateDAO {

	/**
	 * 生成dao
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(CreateFile.DAO_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * @author huangping\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("import ").append(CreateFile.BASE_MAPPER_PACKAGE).append(";\r\n");
		sb.append("import ").append(CreateFile.MODEL_PACKAGE).append(".").append(table.getModelName()).append(";\r\n");
		sb.append("\r\n");
		sb.append("public interface I").append(table.getModelName()).append("DAO extends BaseMapper<").append(table.getModelName()).append("> {\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		String fileName = CreateFile.MAIN_PATH_DIR + CreateFile.JAVA_DIR + "/" + CreateFile.DAO_PACKAGE.replace(".", "/") + "/I"+ table.getModelName() +"DAO.java";
		CreateFile.saveFile(fileName, sb.toString());
	}
}
