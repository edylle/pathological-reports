package com.edylle.pathologicalreports.wrapper;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageWrapper<T> extends PageImpl<T> {

	private static final long serialVersionUID = 9209723031098160323L;

	public PageWrapper(List<T> content) {
		super(content);
	}

	public PageWrapper(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public int getFromElement() {
		return getToElement() - getNumberOfElements() + 1;
	}

	public int getToElement() {
		return ((getNumber() + 1) * getSize()) - (getSize() - getNumberOfElements());
	}

}
