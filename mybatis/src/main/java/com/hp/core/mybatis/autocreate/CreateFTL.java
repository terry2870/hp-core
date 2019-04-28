/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import com.hp.core.freemarker.utils.FreeMarkerUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping 2018年7月11日
 */
public class CreateFTL {

	/**
	 * 生成jsp列表
	 * 
	 * @param table
	 */
	public static void createFTLList(TableBean table) {
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "List.ftl";
		FreeMarkerUtil.createFile("autocreate/pageList.ftl", fileName, table);
	}

	/**
	 * 生成编辑页面
	 * @param table
	 */
	public static void createFTLEdit(TableBean table) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Edit.ftl";
		FreeMarkerUtil.createFile("autocreate/pageEdit.ftl", fileName, table);
	}

	/**
	 * 生成查询页面
	 * @param table
	 */
	public static void createFTLSearch(TableBean table) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_DIR_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Search.ftl";
		FreeMarkerUtil.createFile("autocreate/pageSearch.ftl", fileName, table);
	}
}
