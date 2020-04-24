/**
 * 
 */
package com.hp.core.mail.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.hp.core.mail.request.SendEmailRequestBO;

/**
 * @author ping.huang
 * 2017年3月30日
 */
@Component
public class SendMailHelper {

	private static Logger log = LoggerFactory.getLogger(SendMailHelper.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void sendSimpleMail(SendEmailRequestBO request) {
		log.info("sendSimpleMail start. with request={}", request);

		if (ArrayUtils.isEmpty(request.getTo())) {
			log.warn("sendSimpleMail error. to is empty. with request={}", request);
			return;
		}

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(request.getFrom());
		mailMessage.setTo(request.getTo());

		mailMessage.setSubject(request.getSubject());
		mailMessage.setText(request.getText());
		javaMailSender.send(mailMessage);
		log.info("sendSimpleMail success. with request={}", request);
	}

}
