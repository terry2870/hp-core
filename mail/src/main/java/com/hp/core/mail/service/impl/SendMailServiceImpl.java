/**
 * 
 */
package com.hp.core.mail.service.impl;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hp.core.mail.request.SendEmailRequestBO;
import com.hp.core.mail.service.ISendMailService;

/**
 * @author huangping
 * May 26, 2020
 */
@Service
public class SendMailServiceImpl implements ISendMailService {

	private static Logger log = LoggerFactory.getLogger(SendMailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendSimpleMail(SendEmailRequestBO request) {
		log.debug("sendSimpleMail with request={}", request);
		
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
		log.debug("sendSimpleMail success. with request={}", request);
	}

	@Override
	public void sendHtmlMail(SendEmailRequestBO request) {
		log.debug("sendHtmlMail with request={}", request);

		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			// 邮件发送人
			messageHelper.setFrom(request.getFrom());
			// 邮件接收人
			messageHelper.setTo(request.getTo());
			// 邮件主题
			message.setSubject(request.getSubject());
			// 邮件内容，html格式
			messageHelper.setText(request.getText(), true);
			// 发送
			javaMailSender.send(message);
		} catch (Exception e) {
			log.error("sendHtmlMail error. with request={}", request, e);
		}
	}

}
