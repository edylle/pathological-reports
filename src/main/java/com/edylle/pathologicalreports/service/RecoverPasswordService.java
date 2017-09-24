package com.edylle.pathologicalreports.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.edylle.pathologicalreports.exception.EmailException;
import com.edylle.pathologicalreports.exception.TokenException;
import com.edylle.pathologicalreports.model.entity.RecoverPassword;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.vo.PasswordVO;
import com.edylle.pathologicalreports.repository.RecoverPasswordRepository;

@Service
public class RecoverPasswordService {

	@Autowired
	private RecoverPasswordRepository recoverPasswordRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	public RecoverPassword findByToken(String token) {
		return recoverPasswordRepository.findOne(token);
	}

	public List<RecoverPassword> findByUser(User user) {
		return recoverPasswordRepository.findByUser(user);
	}

	public void delete(RecoverPassword recoverPassword) {
		recoverPasswordRepository.delete(recoverPassword);
	}

	@Transactional
	public void deleteEntitiesBefore(Date date) {
		recoverPasswordRepository.deleteByDateRequested(date);
	}

	@Async
	@Transactional
	public void recoverPasswordBy(String email) throws EmailException {
		User user = userService.findByEmail(email);

		if (user != null) {
			RecoverPassword recoverPassword = new RecoverPassword(user);

			emailService.send(email, recoverPassword.getToken());
			recoverPasswordRepository.save(recoverPassword);
		}
	}

	public void updatePassword(PasswordVO vo, String token) throws Exception {
		validateUpdate(vo);
		RecoverPassword recoverPassword = findByToken(token);

		if (recoverPassword == null) {
			throw new TokenException();
		}

		User usuario = recoverPassword.getUser();
		usuario.setPassword(vo.getPassword());

		try {
			userService.update(usuario);
		} catch (Exception e) {
			throw new Exception("Sorry, an error has occurred.");
		}

		delete(recoverPassword);
	}

	private void validateUpdate(PasswordVO vo) throws IllegalArgumentException {
		if (!vo.getPassword().equals(vo.getConfirmPassword())) {
			throw new IllegalArgumentException("The passwords don't match");
		}

		if (!StringUtils.isEmpty(vo.getPassword()) && vo.getPassword().length() < 5) {
			throw new IllegalArgumentException("The new password must have 5 characters at least");
		}
	}

}
