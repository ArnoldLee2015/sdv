package com.lee.sdv.service.mail.impl;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lee.sdv.service.mail.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	protected static final Logger LOG = LoggerFactory.getLogger(EmailServiceImpl.class);
	@Autowired
	private JavaMailSender autoMailSender;

	@Override
	public void sendEmail(String from, String to, String subject, String content) {
		MimeMessage mailMessage = autoMailSender.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			// 设置收件人，寄件人
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			// 主题/标题
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);

			autoMailSender.send(mailMessage);
		} catch (Exception e) {
			LOG.error("邮件发送失败[{}]", e.getMessage(), e);
		}

	}

}
