/**
 * 
 */
package com.hp.core.mybatis.autocreate;
/**
 * 自动生成model对象
 * @author huangping
 * 2018年5月30日
 */

import com.hp.core.common.utils.DateUtil;

public class CreateModel {

	/**
	 * 生成model文件
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		sb.append("package ").append(CreateFile.MODEL_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("import javax.persistence.Id;\r\n");
		sb.append("\r\n");
		sb.append("import ").append(CreateFile.BASE_BEAN_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * @author huangping\r\n");
		sb.append(" * ").append(DateUtil.getCurrentDateString()).append("\r\n");
		sb.append(" */\r\n");
		sb.append("public class ").append(table.getModelName()).append(" extends BaseBean {\r\n");
		sb.append("\r\n");
		sb.append("	private static final long serialVersionUID = 1L;\r\n");
		sb.append("\r\n");
		
		for (ColumnBean column : table.getColumnList()) {
			sb.append("	/**\r\n");
			sb.append("	 * ").append(column.getColumnComment()).append("\r\n");
			sb.append("	 */\r\n");
			if ("PRI".equalsIgnoreCase(column.getConstraintType())) {
				//主键
				sb.append("	@Id\r\n");
			}
			sb.append("	private Integer ").append(column.getFieldName()).append(";\r\n");
			sb.append("\r\n");
		}
		
		for (ColumnBean column : table.getColumnList()) {
			sb.append("	public Integer get").append(column.getFieldNameFirstUpper()).append("() {\r\n");
			sb.append("		return ").append(column.getFieldName()).append(";\r\n");
			sb.append("	}\r\n");
			sb.append("\r\n");
			sb.append("	public void set").append(column.getFieldNameFirstUpper()).append("(Integer ").append(column.getFieldName()).append(") {\r\n");
			sb.append("		this.").append(column.getFieldName()).append(" = ").append(column.getFieldName()).append(";\r\n");
			sb.append("	}\r\n");
			sb.append("\r\n");
		}
		sb.append("}\r\n");
		
		String fileName = CreateFile.MAIN_PATH_DIR + CreateFile.JAVA_DIR + "/" + CreateFile.MODEL_PACKAGE.replace(".", "/") + "/"+ table.getModelName() +".java";
		CreateFile.saveFile(fileName, sb.toString());
	}
}
