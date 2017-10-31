package com.edylle.pathologicalreports.model.enumeration;

import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edylle.pathologicalreports.utils.Messages;

public enum SexEnum {

	MALE("sex.description.male"),
	FEMALE("sex.description.female");

	@Component
	public static class EnumServiceInjector {
		@Autowired
		private Messages messages;

		@PostConstruct
		public void postConstruct() {
			for (SexEnum role : EnumSet.allOf(SexEnum.class))
				role.setMessages(messages);
		}
	}

	private Messages messages;
	private String description;

	SexEnum(String description) {
		this.description = description;
	}

	// GETTERS AND SETTERS
	public String getDescription() {
		return messages.getMessageBy(description);
	}

	public void setMessages(Messages messages) {
		this.messages = messages;
	}

}
