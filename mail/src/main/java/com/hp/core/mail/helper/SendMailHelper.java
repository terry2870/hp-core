/**
 * 
 */
package com.hp.core.mail.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author ping.huang
 * 2017年3月30日
 */
public class SendMailHelper {

	private static Logger log = LoggerFactory.getLogger(SendMailHelper.class);
	
	private JavaMailSenderImpl javaMailSenderImpl;
	
	@Value("${hp.core.mail.send.from:}")
	private String from;
	
	/**
	 * 发送邮件
	 * @param message
	 */
	public void sendSimpleMailMessage(SimpleMailMessage message) {
		log.info("sendSimpleMailMessage start. with message={}", message);
		if (ArrayUtils.isEmpty(message.getTo())) {
			log.warn("sendSimpleMailMessage error. to is empty. with message={}", message);
			return;
		}
		if (StringUtils.isEmpty(message.getFrom())) {
			message.setFrom(from);
		}
		javaMailSenderImpl.send(message);
		log.info("sendSimpleMailMessage success. with message={}", message);
	}

	public void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
		this.javaMailSenderImpl = javaMailSenderImpl;
	}
}
