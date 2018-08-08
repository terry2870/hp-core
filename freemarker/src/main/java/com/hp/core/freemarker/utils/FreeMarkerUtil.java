/**
 * 
 */
package com.hp.core.freemarker.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.hp.core.common.utils.SpringContextUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author huangping 2018年7月11日
 */
public class FreeMarkerUtil {

	private static Logger log = LoggerFactory.getLogger(FreeMarkerUtil.class);
	
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


}
