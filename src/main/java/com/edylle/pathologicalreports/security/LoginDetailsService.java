package com.edylle.pathologicalreports.security;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.repository.UserRepository;

@Service
public class LoginDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	final String ROLE_PREFIX = "ROLE_";
	private EmailValidator emailValidator = new EmailValidator();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;

		if (emailValidator.isValid(username, null)) {
			user = userRepository.findByEmail(username);
		} else {
			user = userRepository.findByUsername(username);
		}

		if (user == null || !user.getActive())
			throw new UsernameNotFoundException("User " + username + " not found or not active");

		return new LoggedUser(user.getUsername(), user.getPassword(), getAutorizacoes(user), user);
	}

	private Set<GrantedAuthority> getAutorizacoes(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (RoleEnum role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX.concat(role.name())));
		}

		return authorities;
	}

}
