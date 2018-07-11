/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.common.utils.FreeMarkerUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping 2018年7月11日
 */
public class CreateJSP {

	/**
	 * 生成jsp列表
	 * 
	 * @param table
	 */
	public static void createJSPList(TableBean table) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.JSP_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "List.jsp";
		FreeMarkerUtil.createFile("jspList.ftl", fileName, table);
	}

	/**
	 * 生成编辑页面
	 * @param table
	 */
	public static void createJSPEdit(TableBean table) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.JSP_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Edit.jsp";
		FreeMarkerUtil.createFile("jspEdit.ftl", fileName, table);
	}

	/**
	 * 生成查询页面
	 * @param table
	 */
	public static void createJSPSearch(TableBean table) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.JSP_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Search.jsp";
		FreeMarkerUtil.createFile("jspSearch.ftl", fileName, table);
	}
}
