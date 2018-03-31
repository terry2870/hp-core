/**
 * 
 */
package com.hp.core.mybatis.config;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author huangping
 * 2018年4月1日 上午1:31:34
 */
public class LoadDatabaseConfig {

	private String databaseFileName;
	
	public void init() {
		try {
			String str = FileUtils.readFileToString(new File(this.databaseFileName), "UTF-8");
			System.out.println(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDatabaseFileName() {
		return databaseFileName;
	}

	public void setDatabaseFileName(String databaseFileName) {
		this.databaseFileName = databaseFileName;
	}
}
