package com.edylle.pathologicalreports.utils;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Messages {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource);

	}

	public String getMessageBy(String value, Locale locale) {
		if (locale == null) {
			locale = new Locale("pt"); // Portuguese
			if (!locale.getLanguage().equals(LocaleContextHolder.getLocaleContext().getLocale().getLanguage())) {
				locale = Locale.ENGLISH;
			}
		}

		return accessor.getMessage(value, locale);
	}

}
