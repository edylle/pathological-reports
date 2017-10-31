package com.edylle.pathologicalreports.model.enumeration;

import java.util.EnumSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edylle.pathologicalreports.utils.Messages;

public enum SpeciesEnum {

	CANINE("species.description.canine"),
	CAPRINE("species.description.caprine"),
	RABBIT("species.description.rabbit"),
	FELINE("species.description.feline"),
	SWINE("species.description.swine"),
	EQUINE("species.description.equine"),
	SHEEP("species.description.sheep"),
	DOMESTIC_BIRD("species.description.domestic.bird"),
	WILD("species.description.wild");

	@Component
	public static class EnumServiceInjector {
		@Autowired
		private Messages messages;

		@PostConstruct
		public void postConstruct() {
			for (SpeciesEnum role : EnumSet.allOf(SpeciesEnum.class))
				role.setMessages(messages);
		}
	}

	private Messages messages;
	private String description;

	SpeciesEnum(String description) {
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
