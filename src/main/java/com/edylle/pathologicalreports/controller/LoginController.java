package com.edylle.pathologicalreports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.EmailException;
import com.edylle.pathologicalreports.service.RecoverPasswordService;
import com.edylle.pathologicalreports.utils.Messages;
import com.edylle.pathologicalreports.utils.UserUtils;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private RecoverPasswordService recoverPasswordService;
	@Autowired
	 Messages messages;

	@RequestMapping
	public String login() {
		if (UserUtils.isUserLogged()) {
			if (UserUtils.getUser().getRoles().size() == 1) {
				return "redirect:".concat(UserUtils.getUser().getRoles().iterator().next().getHomeUrl());
			} else {
				return "redirect:".concat("/login/select-role");
			}
		}

		return "login";
	}

	@RequestMapping("login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMessage", messages.getMessageBy("authentication.failed", null));

		return "login";
	}

	@RequestMapping("access-denied")
	public String accessDenied(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMessage", messages.getMessageBy("access.denied", null));

		return "login";
	}

	@RequestMapping("logout")
	public String logout(Model model) {
		model.addAttribute("successMessage", messages.getMessageBy("message.logged.out", null));

		return "login";
	}

	@RequestMapping(value = "/recover-password", method = RequestMethod.POST)
	public ModelAndView recoverPassword(RedirectAttributes attributes, @RequestParam("email") String email) throws EmailException {
		recoverPasswordService.recoverPasswordBy(email);

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("successMessage", messages.getMessageBy("message.email.instructions", null));
		return mv;
	}

	@RequestMapping("select-role")
	public ModelAndView selectRole() {
		ModelAndView mv = new ModelAndView("select-role");
		mv.addObject("roles", UserUtils.getUser().getRoles());

		return mv;
	}
}
