package com.edylle.pathologicalreports.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.CrudException;
import com.edylle.pathologicalreports.exception.ImageFormatException;
import com.edylle.pathologicalreports.model.constant.NavIds;
import com.edylle.pathologicalreports.model.vo.UserVO;
import com.edylle.pathologicalreports.service.UserService;
import com.edylle.pathologicalreports.utils.Messages;
import com.edylle.pathologicalreports.utils.UserUtils;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private UserService userService;
	@Autowired
	private Messages messages;

	@ModelAttribute("getNavIds")
	public NavIds getNavIds() {
		return NavIds.getInstance();
	}

	@RequestMapping(value = "/my-account")
	public ModelAndView viewUser() {
		ModelAndView mv = new ModelAndView();

		mv.addObject("navActive", NavIds.getInstance().getUsersStudent());
		mv.setViewName("users/student/new-user");
		mv.addObject("user", new UserVO(userService.findByUsername(UserUtils.getUser().getUsername()), false));

		return mv;
	}

	@RequestMapping(value = "/new-user", method = RequestMethod.POST)
	public String newRegistryPost(Model model, @ModelAttribute("user") @Validated UserVO user, Errors errors, RedirectAttributes attributes, @RequestParam("image") MultipartFile imageFile) {
		try {
			if (errors.hasErrors()) {
				model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());
				return "users/student/new-user";
			}

			userService.save(user, imageFile);

			String message = user.isNewRegistry() ? "message.param.created" : "message.param.updated";
			attributes.addFlashAttribute("successMessage", messages.getMessageBy(message, messages.getMessageBy("label.user")));

			return "redirect:/student/my-account";

		} catch (ImageFormatException e) {
			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());
			model.addAttribute("errorMessage", messages.getMessageBy("message.invalid.image.format"));

			return "users/student/my-account";

		} catch (CrudException e) {
			for (Map.Entry<String, String> entry : e.getRejectedValues().entrySet()) {
				errors.rejectValue(entry.getKey(), null, entry.getValue());
			}

			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());

			return "users/student/new-user";

		} catch (Exception e) {
			model.addAttribute("errorMessage", messages.getMessageBy("message.sorry.error"));
			model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());

			return "users/student/new-user";
		}
	}
}
