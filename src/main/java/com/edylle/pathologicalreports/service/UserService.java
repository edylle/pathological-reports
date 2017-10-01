package com.edylle.pathologicalreports.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edylle.pathologicalreports.model.dto.FindUserDTO;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void update(User newUser) throws Exception {
		User oldUser = userRepository.getOne(newUser.getUsername());

		// TODO turn oldUser into newUser

		userRepository.save(oldUser);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findBy(FindUserDTO dto) {
		String roleStr = dto.getRole() == null ? "%%" : dto.getRole().name();
		String usernameOrEmail;

		if (StringUtils.isEmpty(dto.getUsernameOrEmail())) {
			usernameOrEmail = "%%";
		} else {
			usernameOrEmail = "%".concat(dto.getUsernameOrEmail().toLowerCase()).concat("%");
		}

		return userRepository.findBy(roleStr, usernameOrEmail);
	}

}
