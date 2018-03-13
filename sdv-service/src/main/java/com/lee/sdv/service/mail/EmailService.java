package com.lee.sdv.service.mail;

public interface EmailService {
	public void sendEmail(String from, String to, String subject, String content);
}
