package com.edylle.pathologicalreports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.BusinessException;
import com.edylle.pathologicalreports.model.vo.PasswordVO;
import com.edylle.pathologicalreports.service.RecoverPasswordService;
import com.edylle.pathologicalreports.utils.Messages;

@Controller
@RequestMapping("/recover-password")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecoverPasswordController {

	@Autowired
	private RecoverPasswordService recoverPasswordService;
	@Autowired
	private Messages messages;

	private String token;

	@RequestMapping(value = "/recover")
	public ModelAndView recoverPassword(@RequestParam(name = "token", value = "", required = false) String token) {
		ModelAndView mv = new ModelAndView("recover-password");

		if (!StringUtils.isEmpty(token) && recoverPasswordService.findByToken(token) != null) {
			this.token = token;
			mv.addObject("infoMessage", messages.getMessageBy("label.type.new.password"));

		} else {
			mv.addObject("errorMessage", messages.getMessageBy("message.token.exception"));
		}

		mv.addObject("password", new PasswordVO());

		return mv;
	}

	@RequestMapping(value = "/redefine-password", method = RequestMethod.POST)
	public ModelAndView redefinePassword(@ModelAttribute("password") @Validated PasswordVO password, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return new ModelAndView("recover-password");
		}

		ModelAndView mv = new ModelAndView();

		try {
			recoverPasswordService.updatePassword(password, token);

			mv.addObject("successMessage", messages.getMessageBy("message.password.updated"));
			mv.setViewName("redirect:/login");

			token = null;
			return mv;
		} catch (BusinessException e) {
			mv.addObject("errorMessage", e.getMessage());
			mv.setViewName("recover-password");

			return mv;

		} catch (Exception e) {
			mv.addObject("errorMessage", messages.getMessageBy("message.sorry.error"));
			mv.setViewName("redirect:/login");

			token = null;
			return mv;
		}
	}

}
