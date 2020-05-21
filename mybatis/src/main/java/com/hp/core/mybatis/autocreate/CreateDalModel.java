/**
 * 
 */
package com.hp.core.mybatis.autocreate;
/**
 * 自动生成model对象
 * @author huangping
 * 2018年5月30日
 */

import java.util.HashMap;
import java.util.Map;

import com.hp.core.freemarker.utils.FreeMarkerUtil;
import com.hp.core.mybatis.autocreate.helper.TableBean;

public class CreateDalModel {

	/**
	 * 生成model文件
	 * @param table
	 */
	public static void create(TableBean table) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME + "." + CreateFile.MODEL_DIR_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.DAL_DIR_NAME + "/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/"+ table.getModelName() +".java";
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("baseBeanPackage", CreateFile.BASE_BEAN_PACKAGE);
		map.put("columnList", table.getColumnList());
		map.put("modelName", table.getModelName());
		map.put("primaryKey", table.getPrimaryKey());
		map.put("tableComment", table.getTableComment());
		FreeMarkerUtil.createFile("autocreate/dalmodel.ftl", fileName, map);
	}
}
