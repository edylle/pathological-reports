package com.edylle.pathologicalreports.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.edylle.pathologicalreports.exception.BusinessException;
import com.edylle.pathologicalreports.exception.EmailException;
import com.edylle.pathologicalreports.exception.InfrastructureException;
import com.edylle.pathologicalreports.exception.TokenException;
import com.edylle.pathologicalreports.model.entity.RecoverPassword;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.vo.PasswordVO;
import com.edylle.pathologicalreports.repository.RecoverPasswordRepository;
import com.edylle.pathologicalreports.utils.Messages;

@Service
public class RecoverPasswordService {

	@Autowired
	private RecoverPasswordRepository recoverPasswordRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserService userService;
	@Autowired
	private Messages messages;

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
			throw new TokenException(messages.getMessageBy("message.token.exception"));
		}

		User usuario = recoverPassword.getUser();
		usuario.setPassword(vo.getPassword());

		try {
			userService.update(usuario);
		} catch (Exception e) {
			throw new InfrastructureException(messages.getMessageBy("message.sorry.error"));
		}

		delete(recoverPassword);
	}

	private void validateUpdate(PasswordVO vo) throws BusinessException {
		if (!vo.getPassword().equals(vo.getConfirmPassword())) {
			throw new BusinessException(messages.getMessageBy("message.passwords.dont.match"));
		}
		if (!StringUtils.isEmpty(vo.getPassword()) && vo.getPassword().length() < 5 || vo.getPassword().length() > 16) {
			throw new BusinessException(messages.getMessageBy("message.new.password.lenght.validation"));
		}
	}

}
