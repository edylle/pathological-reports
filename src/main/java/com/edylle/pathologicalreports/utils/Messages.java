package com.edylle.pathologicalreports.utils;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Messages {

	@Autowired
	private MessageSource messageSource;

	private MessageSourceAccessor accessor;
	private Locale portugueLocale;

	@PostConstruct
	private void init() {
		accessor = new MessageSourceAccessor(messageSource);
		portugueLocale = new Locale("pt");
	}

	public String getMessageBy(String value) {
		if (!portugueLocale.getLanguage().equals(LocaleContextHolder.getLocaleContext().getLocale().getLanguage())) {
			return accessor.getMessage(value, Locale.ENGLISH);
		}

		return accessor.getMessage(value, portugueLocale);
	}

	public String getMessageBy(String value, Locale locale) {
		if (locale == null) {
			return getMessageBy(value);
		}

		return accessor.getMessage(value, locale);
	}

	public String getMessageBy(String key, String... params) {
		return setParams(getMessageBy(key), params);
	}

	public String getMessageBy(String key, Locale locale, String... params) {
		return setParams(getMessageBy(key, locale), params);
	}

	private String setParams(String message, String... params) {
		if (!StringUtils.isEmpty(message) && params != null && params.length != 0) {
			for (int i = 0; i < params.length; i++) {
				message = message.replace("{" + i + "}", params[i]);
			}
		}
		return message;
	}

}
