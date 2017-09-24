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

@Service
public class EmailService {

	@Autowired
	private EmailProperties emailProperties;

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

			message.setSubject("Pathological Reports - Recover password");
			message.setText(createBody(emailProperties.getApplicationCtxPath(), token));

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
			throw new EmailException();
		}
	}

	private String createBody(String contextPath, String token) {
		StringBuilder sb = new StringBuilder();

		sb.append("A request to redefine your password was made.")
		  .append("\n")
		  .append("Click the link below to redefine your password:")
		  .append("\n\n")
		  .append(contextPath)
		  .append("/recuperar-senha-web")
		  .append("/recuperar")
		  .append("?token=")
		  .append(token)
		  .append("\n\n")
		  .append("If you did not make this request, please ignore this e-mail.")
		  .append("\n\n")
		  .append("Best regards, Pathological Reports team");

		return sb.toString();
	}
	
}
