package com.edylle.pathologicalreports.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
