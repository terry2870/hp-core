/**
 * 
 */
package com.hp.core.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author huangping 2018年7月11日
 */
public class FreeMarkerUtil {

	private static Logger log = LoggerFactory.getLogger(FreeMarkerUtil.class);
	
	/**
	 * 使用FreeMarker 模板生成文件
	 * @param templateFileName
	 * @param outFileName
	 * @param root
	 */
	public static void createFile(String templateFileName, String outFileName, Object root) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		String templatePath = templateFileName.contains("/") ? templateFileName.substring(0, templateFileName.lastIndexOf("/")) : "./";
		String templateName = templateFileName.substring(templateFileName.lastIndexOf("/") + 1);
		
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
			cfg.setClassForTemplateLoading(FreeMarkerUtil.class, templatePath);
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
			Template temp = cfg.getTemplate(templateName);
			temp.process(root, bw);
			bw.flush();
		} catch (Exception e) {
			log.error("createFile error. with error=", e);
		}
	}

}
