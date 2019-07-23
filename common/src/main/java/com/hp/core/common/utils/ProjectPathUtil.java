/**
 * 
 */
package com.hp.core.common.utils;

import java.io.File;

import org.springframework.boot.system.ApplicationHome;

/**
 * @author huangping
 * May 27, 2019
 */
public class ProjectPathUtil {

	/**
	 * 获取项目根路径
	 * @return
	 */
	public static String getRootPath() {
		ApplicationHome h = new ApplicationHome(ProjectPathUtil.class);
        File jarF = h.getSource();
        return jarF.getParentFile().toString();
	}
}
