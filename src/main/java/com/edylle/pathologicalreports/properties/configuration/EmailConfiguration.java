package com.edylle.pathologicalreports.properties.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.edylle.pathologicalreports.properties.EmailProperties;
import com.edylle.pathologicalreports.properties.sender.EmailSender;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfiguration {

	@Autowired
	private EmailProperties emailProperties;

	@Bean
	public EmailSender emailender() {
		return new EmailSender(emailProperties);
	}

}
