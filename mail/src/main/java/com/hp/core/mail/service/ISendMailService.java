/**
 * 
 */
package com.hp.core.mail.service;

import com.hp.core.mail.request.SendEmailRequestBO;

/**
 * @author huangping
 * May 26, 2020
 */
public interface ISendMailService {

	/**
	 * 发送普通文本邮件
	 * @param request
	 */
	public void sendSimpleMail(SendEmailRequestBO request);
	
	/**
	 * 发送带html标记的邮件
	 * @param request
	 */
	public void sendHtmlMail(SendEmailRequestBO request);
}
