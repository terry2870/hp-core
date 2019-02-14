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
 * 2018年5月31日
 */
public class CreateMapper {

	/**
	 * 生成mapper文件
	 * @param table
	 */
	public static void create(TableBean table) {
		String dalPackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAL_DIR_NAME +"/" + CreateFile.MAPPING_DIR + "/"+ table.getModelName() +"Mapper.xml";
		Map<String, Object> map = new HashMap<>();
		map.put("dalPackage", dalPackage);
		map.put("dalModelPackage", dalPackage + "." + CreateFile.MODEL_DIR_NAME);
		map.put("modelName", table.getModelName());
		FreeMarkerUtil.createFile("autocreate/mapper.ftl", fileName, map);
	}
}
