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
 * 2018年6月4日
 */
public class CreateController {

	public static void create(TableBean table) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.CONTROLLER_MAVEN_MODULE + "." + CreateFile.CONTROLLER_DIR_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.CONTROLLER_MAVEN_MODULE + "/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/")
				+ "/" + table.getModelName() + "Controller.java";
		Map<String, Object> map = new HashMap<>();
		map.put("modelName", table.getModelName());
		map.put("modelNameFirstLow", table.getModelNameFirstLow());
		map.put("tableComment", table.getTableComment());
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("freeMarkerUtilPackage", CreateFile.FREEMARKER_UTIL_PACKAGE);
		map.put("responsePackage", CreateFile.RESPONSE_PACKAGE);
		map.put("pageRequestPackage", CreateFile.BASE_PAGE_REQUEST_PACKAGE);
		map.put("pageResponsePackage", CreateFile.BASE_PAGE_RESPONSE_PACKAGE);
		map.put("requestModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.REQUEST_DIR_NAME);
		map.put("responseModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.RESPONSE_DIR_NAME);
		map.put("servicePackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_MAVEN_MODULE + "." + CreateFile.SERVICE_DIR_NAME);
		FreeMarkerUtil.createFile("autocreate/controller.ftl", fileName, map);
	}
}
