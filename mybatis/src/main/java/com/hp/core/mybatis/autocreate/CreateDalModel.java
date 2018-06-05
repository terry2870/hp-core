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
import com.hp.core.mybatis.autocreate.helper.ColumnBean;
import com.hp.core.mybatis.autocreate.helper.TableBean;

public class CreateDalModel {

	/**
	 * 生成model文件
	 * @param table
	 */
	public static void create(TableBean table) {
		StringBuilder sb = new StringBuilder();
		String packageUrl = CreateFile.PROJECT_PACKAGE + ".dal.model";
		sb.append("package ").append(packageUrl).append(";\r\n");
		sb.append("\r\n");
		sb.append("import javax.persistence.Id;\r\n");
		sb.append("\r\n");
		sb.append("import ").append(CreateFile.BASE_BEAN_PACKAGE).append(";\r\n");
		sb.append("\r\n");
		sb.append("/**\r\n");
		sb.append(" * @author ").append(CreateFile.AUTHER_NAME).append("\r\n");
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
			if (table.getPrimaryKey().equals(column.getColumnName())) {
				//主键
				sb.append("	@Id\r\n");
			}
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
		
		String fileName = CreateFile.MAIN_PATH_DIR + CreateFile.DAL_DIR_NAME +"/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/"+ table.getModelName() +".java";
		CreateFile.saveFile(fileName, sb.toString());
	}
}
