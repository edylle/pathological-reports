package com.edylle.pathologicalreports.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edylle.pathologicalreports.exception.CrudException;
import com.edylle.pathologicalreports.model.dto.FindUserDTO;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.model.vo.UserVO;
import com.edylle.pathologicalreports.properties.ImagePathProperties;
import com.edylle.pathologicalreports.repository.UserRepository;
import com.edylle.pathologicalreports.utils.FilesUtils;
import com.edylle.pathologicalreports.utils.Messages;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ImagePathProperties imagePathProperties;
	@Autowired
	private Messages messages;

	public User save(User user) throws Exception {
		return userRepository.save(user);
	}

	public User save(UserVO user, MultipartFile imageFile) throws Exception {
		Map<String, String> rejectedValues = new HashMap<>();

		// creating a new user
		if (user.isNewUser()) {

			if (findByUsername(user.getUsername()) != null) {
				rejectedValues.put("username", messages.getMessageBy("message.param.duplicated", messages.getMessageBy("placeholder.username")));
			}
			if (findByEmail(user.getEmail()) != null) {
				rejectedValues.put("email", messages.getMessageBy("message.param.duplicated", messages.getMessageBy("placeholder.email")));
			}
			if (StringUtils.isEmpty(user.getPassword())) {
				rejectedValues.put("password", messages.getMessageBy("validation.field.required.password"));
			}
			if (StringUtils.isNotEmpty(user.getPassword()) && user.getPassword().trim().length() < 5 || user.getPassword().trim().length() > 16) {
				rejectedValues.put("password", messages.getMessageBy("validation.length.password"));
			}
			if (StringUtils.isEmpty(user.getConfirmPassword())) {
				rejectedValues.put("confirmPassword", messages.getMessageBy("validation.field.required.confirm.password"));
			}
			if (StringUtils.isNotEmpty(user.getPassword()) && StringUtils.isNotEmpty(user.getConfirmPassword())) {
				if (!user.getPassword().equals(user.getConfirmPassword())) {
					rejectedValues.put("password", messages.getMessageBy("message.passwords.dont.match"));
				}
			}
			
		// updating a previously created user
		} else {

		}

		if (!rejectedValues.isEmpty()) {
			throw new CrudException(rejectedValues);
		}

		if (imageFile != null && !imageFile.isEmpty()) {
			user.setImagePath(FilesUtils.saveImage(imagePathProperties.getUserContext(), imagePathProperties.getUsersPath(), imageFile, user.getUsername()));
		}

		return save(new User(user));
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<User> findBy(FindUserDTO dto) {
		List<String> roleStr;
		String usernameOrEmail;

		if (dto.getRole() == null) {
			roleStr = Arrays.asList(RoleEnum.PROFESSOR.name(), RoleEnum.STUDENT.name());
		} else {
			roleStr = Arrays.asList(dto.getRole().name());
		}

		if (StringUtils.isEmpty(dto.getUsernameOrEmail())) {
			usernameOrEmail = "%%";
		} else {
			usernameOrEmail = "%".concat(dto.getUsernameOrEmail().toLowerCase()).concat("%");
		}

		return userRepository.findBy(roleStr, usernameOrEmail);
	}

}
