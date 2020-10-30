package com.hp.core.mail.request;

import java.io.Serializable;

/**
 * @Description: 
 * @author: huangping
 * @date: 2020-10-26 10:40:37
 */
public class MailFileBO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6496333285544250994L;
	
	
	private String fileName;
	
	
	public MailFileBO() {}

	public MailFileBO(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
}
