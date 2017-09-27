package com.edylle.pathologicalreports.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edylle.pathologicalreports.exception.EmailException;
import com.edylle.pathologicalreports.properties.EmailProperties;
import com.edylle.pathologicalreports.utils.Messages;

@Service
public class EmailService {

	@Autowired
	private EmailProperties emailProperties;
	@Autowired
	private Messages messages;

	public void send(String email, String token) throws EmailException {
		Properties properties = System.getProperties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", emailProperties.getSmtpHost());
		properties.put("mail.smtp.port", emailProperties.getSmtpPort());

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailProperties.getUsername(), emailProperties.getPassword());
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailProperties.getUsername()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			message.setSubject(messages.getMessageBy("label.pathological.reports").concat(" - ").concat(messages.getMessageBy("label.recover.password")));
			message.setText(createBody(emailProperties.getApplicationCtxPath(), token));

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			throw new EmailException(messages.getMessageBy("message.email.exception"));
		}
	}

	private String createBody(String contextPath, String token) {
		return messages.getMessageBy("message.email.body", contextPath, token);
	}
	
}
