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
public class CreateDAO {

	/**
	 * 生成dao
	 * @param table
	 */
	public static void create(TableBean table) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAL_DIR_NAME +"/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/I"+ table.getModelName() +"DAO.java";
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("modelName", table.getModelName());
		map.put("dalModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_DIR_NAME + "." + CreateFile.MODEL_DIR_NAME);
		map.put("tableComment", table.getTableComment());
		map.put("baseMapperPackage", CreateFile.BASE_MAPPER_PACKAGE);
		FreeMarkerUtil.createFile("autocreate/dal.ftl", fileName, map);
	}
}
