package com.edylle.pathologicalreports.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "OWNER")
public class Owner implements Serializable {

	private static final long serialVersionUID = -8430919133896218180L;

	@Id
	@NotEmpty(message = "{validation.field.required.client.id}")
	@Size(max = 32, message = "{validation.length.client.id}")
	@Column(name = "ID", length = 32)
	private String Id;

	@NotEmpty(message = "{validation.field.required.name}")
	@Size(max = 128, message = "{validation.length.name}")
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;

	@NotEmpty(message = "{validation.field.required.phonenumber}")
	@Size(max = 16, message = "{validation.length.phonenumber}")
	@Column(name = "PHONE_NUMBER", length = 16, nullable = false)
	private String phoneNumber;

	@Size(max = 255, message = "{validation.length.address}")
	@Column(name = "ADDRESS", length = 255)
	private String address;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATE_CREATED", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Animal> animals;

	@Transient
	private boolean isNewRegistry;

	public Owner() {
	}

	public Owner(boolean isNewRegistry) {
		this.isNewRegistry = isNewRegistry;

		if (isNewRegistry)
			dateCreated = new Date();
	}

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	public boolean isNewRegistry() {
		return isNewRegistry;
	}

	public void setNewRegistry(boolean isNewRegistry) {
		this.isNewRegistry = isNewRegistry;
	}

}
