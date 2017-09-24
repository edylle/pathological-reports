package com.edylle.pathologicalreports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.TokenException;
import com.edylle.pathologicalreports.model.vo.PasswordVO;
import com.edylle.pathologicalreports.service.RecoverPasswordService;

@Controller
@RequestMapping("/recover-password")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecoverPasswordController {

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	private String token;

	public ModelAndView recoverPassword(@RequestParam("token") String token) {
		ModelAndView mv = new ModelAndView("recover-password");

		if (recoverPasswordService.findByToken(token) != null) {
			this.token = token;
			mv.addObject("successMessageStyle", "Type new password");

		} else {
			mv.addObject("errorMessageStyle", new TokenException().getMessage());
		}

		mv.addObject("password", new PasswordVO());

		return mv;
	}

	@RequestMapping(value = "/redefine-password", method = RequestMethod.POST)
	public ModelAndView redefinePassword(@ModelAttribute("password") @Validated PasswordVO password, Errors errors, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView("recover-password");

		if (errors.hasErrors()) {
			return mv;
		}

		try {
			recoverPasswordService.updatePassword(password, token);

			mv.addObject("successMessageStyle", "Password successfully updated!");

			token = null;
			return mv;
		} catch (Exception e) {
			mv.addObject("errorMessageStyle", e.getMessage());
			return mv;
		}
	}

}
