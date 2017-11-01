package com.edylle.pathologicalreports.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.edylle.pathologicalreports.model.enumeration.SexEnum;
import com.edylle.pathologicalreports.model.enumeration.SpeciesEnum;

@Entity
@Table(name = "ANIMAL")
public class Animal implements Serializable {

	private static final long serialVersionUID = -1079189245216993626L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 12)
	private Long id;

	@NotEmpty(message = "{validation.field.required.name}")
	@Size(max = 128, message = "{validation.length.name}")
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;

	@Column(name = "YEARS_OLD", length = 2, nullable = false)
	private Integer yearsOld;

	@Column(name = "MONTHS_OLD", length = 2, nullable = false)
	private Integer monthsOld;

	@Column(name = "DAYS_OLD", length = 2, nullable = false)
	private Integer daysOld;

	@NotNull(message = "{validation.field.required.sex}")
	@Column(name = "SEX", length = 6, nullable = false)
	@Enumerated(EnumType.STRING)
	private SexEnum sex;

	@NotNull(message = "{validation.field.required.species}")
	@Column(name = "SPECIES", length = 16, nullable = false)
	@Enumerated(EnumType.STRING)
	private SpeciesEnum species;

	@Size(max = 128, message = "{validation.length.breed}")
	@Column(name = "BREED", length = 128)
	private String breed;

	@Size(max = 128, message = "{validation.length.detailed.species}")
	@Column(name = "DETAILED_SPECIES", length = 128)
	private String detailedSpecies;

	@ManyToOne
	@JoinColumn(name = "ID_OWNER", referencedColumnName = "ID", nullable = false)
	private Owner owner;

	// GETTERS AND SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYearsOld() {
		return yearsOld;
	}

	public void setYearsOld(Integer yearsOld) {
		this.yearsOld = yearsOld;
	}

	public Integer getMonthsOld() {
		return monthsOld;
	}

	public void setMonthsOld(Integer monthsOld) {
		this.monthsOld = monthsOld;
	}

	public Integer getDaysOld() {
		return daysOld;
	}

	public void setDaysOld(Integer daysOld) {
		this.daysOld = daysOld;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}

	public SpeciesEnum getSpecies() {
		return species;
	}

	public void setSpecies(SpeciesEnum species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getDetailedSpecies() {
		return detailedSpecies;
	}

	public void setDetailedSpecies(String detailedSpecies) {
		this.detailedSpecies = detailedSpecies;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
