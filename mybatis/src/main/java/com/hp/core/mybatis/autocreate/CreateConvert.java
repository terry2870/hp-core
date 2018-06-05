/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月1日
 */
public class CreateConvert {

	
	/**
	 * 生成convert
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		String packageUrl = CreateFile.PROJECT_PACKAGE + ".common.convert";
		String dalModelPackage = CreateFile.PROJECT_PACKAGE + ".dal.model";
		String requestModelPackage = CreateFile.PROJECT_PACKAGE + ".model.request";
		String responseModelPackage = CreateFile.PROJECT_PACKAGE + ".model.response";
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.COMMON_DIR_NAME +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = table.getModelName() + "Convert";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("import org.springframework.beans.BeanUtils;\r\n");
		sb.append("\r\n");
		sb.append("import ").append(dalModelPackage).append(".").append(table.getModelName()).append(";\r\n");
		sb.append("import ").append(requestModelPackage).append(".").append(table.getModelName()).append("RequestBO;\r\n");
		sb.append("import ").append(responseModelPackage).append(".").append(table.getModelName()).append("ResponseBO;\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * 对象转换类\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("\r\n");
		sb.append("public class ").append(fileName).append(" {\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * bo request --> dal\r\n");
		sb.append("	 * @param bo\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	public static ").append(table.getModelName()).append(" boRequest2Dal(").append(table.getModelName()).append("RequestBO bo) {\r\n");
		sb.append("		if (bo == null) {\r\n");
		sb.append("			return null;\r\n");
		sb.append("		}\r\n");
		sb.append("		").append(table.getModelName()).append(" dal = new ").append(table.getModelName()).append("();\r\n");
		sb.append("		BeanUtils.copyProperties(bo, dal);\r\n");
		sb.append("		return dal;\r\n");
		sb.append("	}\r\n");
		sb.append("\r\n");
		sb.append("	/**\r\n");
		sb.append("	 * dal --> bo response\r\n");
		sb.append("	 * @param dal\r\n");
		sb.append("	 * @return\r\n");
		sb.append("	 */\r\n");
		sb.append("	public static ").append(table.getModelName()).append("ResponseBO dal2BOResponse(").append(table.getModelName()).append(" dal) {\r\n");
		sb.append("		if (dal == null) {\r\n");
		sb.append("			return null;\r\n");
		sb.append("		}\r\n");
		sb.append("		").append(table.getModelName()).append("ResponseBO bo = new ").append(table.getModelName()).append("ResponseBO();\r\n");
		sb.append("		BeanUtils.copyProperties(dal, bo);\r\n");
		sb.append("		return bo;\r\n");
		sb.append("	}\r\n");
		sb.append("}\r\n");
		
		CreateFile.saveFile(filePath + "/" + fileName + ".java", sb.toString());
	}
}
