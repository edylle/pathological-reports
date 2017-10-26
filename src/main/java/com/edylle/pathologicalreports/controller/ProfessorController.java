package com.edylle.pathologicalreports.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.CrudException;
import com.edylle.pathologicalreports.exception.ImageFormatException;
import com.edylle.pathologicalreports.model.constant.NavIds;
import com.edylle.pathologicalreports.model.dto.FindUserDTO;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.model.vo.UserVO;
import com.edylle.pathologicalreports.service.UserService;
import com.edylle.pathologicalreports.utils.Messages;
import com.edylle.pathologicalreports.utils.UserUtils;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

	@Autowired
	private UserService userService;
	@Autowired
	private Messages messages;

	@ModelAttribute("getNavIds")
	public NavIds getNavIds() {
		return NavIds.getInstance();
	}

	@RequestMapping("list-users")
	public ModelAndView findUsers(@ModelAttribute("user") FindUserDTO user,
			                      @RequestParam(name = "warnMessage", value = "", required = false) String warnMessage,
			                      @RequestParam(name = "initialPage", value = "", required = false) String initialPage) {

		ModelAndView mv = new ModelAndView("users/professor/list-users");
		mv.addObject("navActive", NavIds.getInstance().getUsersProfessor());

		int page = 0;
		int size = 5;

		if (StringUtils.isNumeric(initialPage)) {
			page = Integer.valueOf(initialPage);
		}

		mv.addObject("users", userService.findBy(user, page, size));
		mv.addObject("warnMessage", warnMessage);

		mv.addObject("loggedUsername", UserUtils.getUser().getUsername());
		mv.addObject("loggedImage", userService.findByUsername(UserUtils.getUser().getUsername()).getImagePath()); // get updated image

		return mv;
	}

	@RequestMapping("new-user")
	public ModelAndView newUser() {
		ModelAndView mv = new ModelAndView("users/professor/new-user");
		mv.addObject("navActive", NavIds.getInstance().getUsersProfessor());
		mv.addObject("user", new UserVO(true, RoleEnum.STUDENT));

		return mv;
	}

	@RequestMapping(value = "/new-user", method = RequestMethod.POST)
	public String newUserPost(Model model, @ModelAttribute("user") @Validated UserVO user, Errors errors, RedirectAttributes attributes, @RequestParam("image") MultipartFile imageFile) {
		try {
			if (errors.hasErrors()) {
				model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());
				return "users/professor/new-user";
			}

			userService.save(user, imageFile);

			String message = user.isNewUser() ? "message.param.created" : "message.param.updated";
			attributes.addFlashAttribute("successMessage", messages.getMessageBy(message, messages.getMessageBy("label.user")));

			return "redirect:/professor/list-users";

		} catch (ImageFormatException e) {
			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());
			model.addAttribute("errorMessage", messages.getMessageBy("message.invalid.image.format"));

			return "users/professor/new-user";

		} catch (CrudException e) {
			for (Map.Entry<String, String> entry : e.getRejectedValues().entrySet()) {
				errors.rejectValue(entry.getKey(), null, entry.getValue());
			}

			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());

			return "users/professor/new-user";

		} catch (Exception e) {
			model.addAttribute("errorMessage", messages.getMessageBy("message.sorry.error"));
			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());

			return "users/professor/new-user";
		}
	}

	@RequestMapping(value = "/user/{username}")
	public ModelAndView viewUser(@PathVariable("username") User user) {
		ModelAndView mv = new ModelAndView();

		if (user == null) {
			mv.addObject("warnMessage", messages.getMessageBy("message.user.not.found"));
			mv.setViewName("redirect:/professor/list-users");

			return mv;
		}
		if ((user.getRoles().contains(RoleEnum.ADMIN) || user.getRoles().contains(RoleEnum.PROFESSOR)) && !user.equals(UserUtils.getUser())) {
			mv.addObject("warnMessage", messages.getMessageBy("message.not.permission.modify.admin.professor"));
			mv.setViewName("users/professor/list-users");

			return mv;
		}

		mv.addObject("navActive", NavIds.getInstance().getUsersProfessor());
		mv.setViewName("users/professor/new-user");
		mv.addObject("user", new UserVO(user, false));

		return mv;
	}

	@RequestMapping(value = "/activate-user/{username}", method = RequestMethod.POST)
	public String activateUser(@PathVariable String username, RedirectAttributes attributes) {
		try {
			userService.activateUserBy(username);

			attributes.addFlashAttribute("successMessage", messages.getMessageBy("message.param.updated", username));
		} catch (Exception e) {
			attributes.addFlashAttribute("errorMessage", messages.getMessageBy("message.sorry.error"));
		}

		return "redirect:/professor/list-users";
	}

}
