/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.DateUtil;
import com.hp.core.mybatis.autocreate.helper.ColumnBean;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月1日
 */
public class CreatModel {

	/**
	 * 生成request的model
	 * @param table
	 */
	public static void creat(TableBean table, String type) {
		StringBuilder sb = new StringBuilder();
		String packageUrl = "", fileName = "", filePath = "", baseBeanName = "";
		if ("1".equals(type)) {
			//request
			packageUrl = CreateFile.PROJECT_PACKAGE + ".model.request";
			fileName = table.getModelName() + "RequestBO";
			baseBeanName = "BaseRequestBO";
		} else if ("2".equals(type)) {
			//response
			packageUrl = CreateFile.PROJECT_PACKAGE + ".model.response";
			fileName = table.getModelName() + "ResponseBO";
			baseBeanName = "BaseResponseBO";
		}
		filePath = CreateFile.MAIN_PATH_DIR + CreateFile.MODEL_DIR_NAME +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		if ("1".equals(type)) {
			sb.append("import ").append(CreateFile.BASE_REQUEST_BO_PACKAGE).append(";\r\n");
			sb.append("\r\n");
		} else {
			sb.append("import ").append(CreateFile.BASE_RESPONSE_BO_PACKAGE).append(";\r\n");
			sb.append("\r\n");
		}
		sb.append("/**\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("public class ").append(fileName).append(" extends ").append(baseBeanName).append(" {\r\n");
		sb.append("\r\n");
		sb.append("	private static final long serialVersionUID = 1L;\r\n");
		sb.append("\r\n");
		
		for (ColumnBean column : table.getColumnList()) {
			sb.append("	/**\r\n");
			sb.append("	 * ").append(column.getColumnComment()).append("\r\n");
			sb.append("	 */\r\n");
			sb.append("	private ").append(column.getJavaType()).append(" ").append(column.getFieldName()).append(";\r\n");
			sb.append("\r\n");
		}
		
		for (ColumnBean column : table.getColumnList()) {
			sb.append("	public ").append(column.getJavaType()).append(" get").append(column.getFieldNameFirstUpper()).append("() {\r\n");
			sb.append("		return ").append(column.getFieldName()).append(";\r\n");
			sb.append("	}\r\n");
			sb.append("\r\n");
			sb.append("	public void set").append(column.getFieldNameFirstUpper()).append("(").append(column.getJavaType()).append(" ").append(column.getFieldName()).append(") {\r\n");
			sb.append("		this.").append(column.getFieldName()).append(" = ").append(column.getFieldName()).append(";\r\n");
			sb.append("	}\r\n");
			sb.append("\r\n");
		}
		sb.append("}\r\n");
		
		CreateFile.saveFile(filePath + "/" + fileName + ".java", sb.toString());
	}
}
