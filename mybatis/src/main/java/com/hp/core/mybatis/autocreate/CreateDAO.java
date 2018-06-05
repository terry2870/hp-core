/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

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
		String packageUrl = CreateFile.PROJECT_PACKAGE + ".dal";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * 对应的dao接口\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("import ").append(CreateFile.BASE_MAPPER_PACKAGE).append(";\r\n");
		sb.append("import ").append(packageUrl).append(".model.").append(table.getModelName()).append(";\r\n");
		sb.append("\r\n");
		sb.append("public interface I").append(table.getModelName()).append("DAO extends BaseMapper<").append(table.getModelName()).append("> {\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAL_DIR_NAME +"/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/I"+ table.getModelName() +"DAO.java";
		CreateFile.saveFile(fileName, sb.toString());
	}
}
