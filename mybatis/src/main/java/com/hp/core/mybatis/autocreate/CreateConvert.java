/**
 * 
 */
package com.hp.core.mybatis.autocreate;

import java.util.HashMap;
import java.util.Map;

import com.hp.core.freemarker.utils.FreeMarkerUtil;
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
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.COMMON_DIR_NAME + "." + CreateFile.CONVERT_DIR_NAME;
		String filePath = CreateFile.MAIN_PATH_DIR + CreateFile.COMMON_DIR_NAME +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = filePath + "/" + table.getModelName() + "Convert.java";
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("modelName", table.getModelName());
		map.put("tableComment", table.getTableComment());
		map.put("dalModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME + "." + CreateFile.MODEL_DIR_NAME);
		map.put("requestModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.REQUEST_DIR_NAME);
		map.put("responseModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.RESPONSE_DIR_NAME);
		FreeMarkerUtil.createFile("autocreate/convert.ftl", fileName, map);
	}
}
