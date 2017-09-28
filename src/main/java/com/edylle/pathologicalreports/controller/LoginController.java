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
	private Messages messages;

	@RequestMapping
	public String login(Model model,
			@RequestParam(name = "successMessage", value = "", required = false) String sucessMessage,
			@RequestParam(name = "errorMessage", value = "", required = false) String errorMessage) {

		if (UserUtils.isUserLogged()) {
			if (UserUtils.getUser().getRoles().size() == 1) {
				return "redirect:".concat(UserUtils.getUser().getRoles().iterator().next().getHomeUrl());
			} else {
				return "redirect:/login/select-role";
			}
		}

		model.addAttribute("successMessage", sucessMessage);
		model.addAttribute("errorMessage", errorMessage);

		return "login";
	}

	@RequestMapping("login-error")
	public String loginError(Model model) {
		model.addAttribute("errorMessage", messages.getMessageBy("label.authentication.failed"));

		return "login";
	}

	@RequestMapping("access-denied")
	public String accessDenied(Model model) {
		model.addAttribute("errorMessage", messages.getMessageBy("label.access.denied"));

		return "login";
	}

	@RequestMapping("logout")
	public String logout(Model model) {
		model.addAttribute("successMessage", messages.getMessageBy("message.logged.out"));

		return "login";
	}

	@RequestMapping(value = "/recover-password", method = RequestMethod.POST)
	public ModelAndView recoverPassword(RedirectAttributes attributes, @RequestParam("email") String email) throws EmailException {
		recoverPasswordService.recoverPasswordBy(email);

		ModelAndView mv = new ModelAndView("login");
		mv.addObject("successMessage", messages.getMessageBy("message.email.instructions"));

		return mv;
	}

	@RequestMapping("select-role")
	public ModelAndView selectRole() {
		ModelAndView mv = new ModelAndView("select-role");
		mv.addObject("roles", UserUtils.getUser().getRoles());

		return mv;
	}
}
