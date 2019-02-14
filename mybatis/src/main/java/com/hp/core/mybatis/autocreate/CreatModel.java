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
public class CreatModel {

	/**
	 * 生成request的model
	 * @param table
	 */
	public static void creat(TableBean table, String type) {
		String packageUrl = "", fileName = "", filePath = "", baseExtendBeanName = "", importPackage = "";
		if ("1".equals(type)) {
			//request
			packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.REQUEST_DIR_NAME;
			fileName = table.getModelName() + "RequestBO";
			baseExtendBeanName = "BaseRequestBO";
			importPackage = CreateFile.BASE_REQUEST_BO_PACKAGE;
		} else if ("2".equals(type)) {
			//response
			packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_DIR_NAME + "." + CreateFile.RESPONSE_DIR_NAME;
			fileName = table.getModelName() + "ResponseBO";
			baseExtendBeanName = "BaseResponseBO";
			importPackage = CreateFile.BASE_RESPONSE_BO_PACKAGE;
		}
		filePath = CreateFile.MAIN_PATH_DIR + CreateFile.MODEL_DIR_NAME +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		Map<String, Object> map = new HashMap<>();
		map.put("author", CreateFile.AUTHER_NAME);
		map.put("package", packageUrl);
		map.put("className", fileName);
		map.put("importPackage", importPackage);
		map.put("baseExtendBeanName", baseExtendBeanName);
		map.put("columnList", table.getColumnList());
		map.put("tableComment", table.getTableComment());
		FreeMarkerUtil.createFile("autocreate/model.ftl", filePath + "/" + fileName + ".java", map);
	}
}
