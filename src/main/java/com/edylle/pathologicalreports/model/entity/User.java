package com.edylle.pathologicalreports.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.model.vo.UserVO;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = 5716408104446654247L;

	@Id
	@NotEmpty(message = "{validation.field.required.username}")
	@Size(max = 32, message = "{validation.length.username}")
	@Column(name = "USERNAME", length = 32)
	private String username;

	@Email(message = "{validation.email}")
	@NotEmpty(message = "{validation.field.required.email}")
	@Size(max = 128, message = "{validation.length.email}")
	@Column(name = "EMAIL", length = 128, unique = true, nullable = false)
	private String email;

	@NotEmpty(message = "{validation.field.required.phonenumber}")
	@Size(max = 16, message = "{validation.length.phonenumber}")
	@Column(name = "PHONE_NUMBER", length = 16, nullable = false)
	private String phoneNumber;

	@NotEmpty(message = "{validation.field.required.password}")
	@Column(name = "PASSWORD", length = 80, nullable = false)
	private String password;

	@Column(name = "IMAGE_PATH", length = 255)
	private String imagePath;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DATE_CREATED", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateCreated;

	@NotNull
	@Column(name = "ACTIVE", nullable = false)
	private Boolean active;

	@NotNull
	@Cascade({ CascadeType.ALL })
	@ElementCollection(targetClass = RoleEnum.class, fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "USERNAME_USER"))
	@Column(name = "ROLE", length = 16, nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<RoleEnum> roles;

	public User() {
		dateCreated = new Date();
		active = true;
	}

	public User(UserVO vo) {
		this();
		username = vo.getUsername();
		email = vo.getEmail();
		phoneNumber = vo.getPhoneNumber();
		roles = new HashSet<>();
		roles.add(vo.getRole());
		setPassword(vo.getPassword());
	}

	public void orverrideUser(UserVO vo) {
		if (vo.getActive() != null)
			setActive(vo.getActive());

		if (StringUtils.isNotBlank(vo.getEmail()))
			setEmail(vo.getEmail());

		if (StringUtils.isNotBlank(vo.getPhoneNumber()))
			setPhoneNumber(vo.getPhoneNumber());

		if (StringUtils.isNotBlank(vo.getPassword()))
			setPassword(vo.getPassword());

		if (StringUtils.isNotBlank(vo.getImagePath()))
			setImagePath(vo.getImagePath());

		if (vo.getRole() != null) {
			roles = new HashSet<>();
			roles.add(vo.getRole());

			setRoles(roles);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	// GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<RoleEnum> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEnum> roles) {
		this.roles = roles;
	}

}
