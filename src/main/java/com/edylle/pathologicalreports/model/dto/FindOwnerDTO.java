package com.edylle.pathologicalreports.model.dto;

import java.io.Serializable;

public class FindOwnerDTO implements Serializable {

	private static final long serialVersionUID = -5206095343591135641L;

	private String Id;
	private String name;

	// GETTERS AND SETTERS
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
