/**
 * 
 */
package com.hp.core.freemarker.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hp.core.common.utils.SpringContextUtil;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author huangping 2018年7月11日
 */
public class FreeMarkerUtil {

	private static Logger log = LoggerFactory.getLogger(FreeMarkerUtil.class);
	
	private static BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS).build();
	
	/**
	 * 使ftl页面上可以直接调用java静态方法和字段
	 * @param map
	 */
	public static void addStaticTemplate(Map<String, Object> map) {
		addStaticTemplate(map, "javaClass");
	}
	
	/**
	 * 使ftl页面上可以直接调用java静态方法和字段
	 * @param map
	 * @param staticTemplateModelName
	 */
	public static void addStaticTemplate(Map<String, Object> map, String staticTemplateModelName) {
		map.put(staticTemplateModelName, beansWrapper.getStaticModels());
	}
	
	/**
	 * 使用FreeMarker模板生成文件
	 * @param templateFileName
	 * @param outFileName
	 * @param root
	 */
	public static void createFile(String templateFileName, String outFileName, Object root) {
		FreeMarkerConfigurer cfg2 = SpringContextUtil.getBean("local_freeMarkerConfigurer", FreeMarkerConfigurer.class);
		Configuration cfg = cfg2.getConfiguration();
		
		String outPath = outFileName.contains("/") ? outFileName.substring(0,  outFileName.lastIndexOf("/")) : "./";
		File outFile = new File(outPath);
		//文件夹不存在，则创建文件夹
		if (!outFile.exists()) {
			outFile.mkdirs();
		}
		outFile = new File(outFileName);
		try (
				FileWriter fw = new FileWriter(outFile);
				BufferedWriter bw = new BufferedWriter(fw);
			) {
			//cfg.setClassForTemplateLoading(FreeMarkerUtil.class, templatePath);
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
			Template temp = cfg.getTemplate(templateFileName);
			temp.process(root, bw);
			bw.flush();
		} catch (Exception e) {
			log.error("createFile error. with error=", e);
		}
	}

	/**
	 * 根据模板，生成字符串
	 * @param templateName
	 * @param root
	 * @return
	 */
	public static String getStringTemplate(String templateName, Object root) {
		FreeMarkerConfigurer cfg2 = SpringContextUtil.getBean("local_freeMarkerConfigurer", FreeMarkerConfigurer.class);
		Configuration cfg = cfg2.getConfiguration();
		cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
		try (
				StringWriter stringWriter = new StringWriter();
			){
			Template temp = cfg.getTemplate(templateName);
			temp.process(root, stringWriter);
			return stringWriter.toString();
		} catch (Exception e) {
			log.error("getStringTemplate error. with error=", e);
			return null;
		}
		
		
	}
}
