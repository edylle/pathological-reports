package com.edylle.pathologicalreports.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	@Autowired
	private Messages messages;

	@ModelAttribute("getRoles")
	public List<RoleEnum> getAllRoles() {
		return Arrays.asList(RoleEnum.PROFESSOR, RoleEnum.STUDENT);
	}

	@ModelAttribute("getNavIds")
	public NavIds getNavIds() {
		return NavIds.getInstance();
	}

	@RequestMapping("list-users")
	public ModelAndView findUsers(@ModelAttribute("user") FindUserDTO user, @RequestParam(name = "warnMessage", value = "", required = false) String warnMessage) {
		ModelAndView mv = new ModelAndView("users/admin/list-users");
		mv.addObject("navActive", NavIds.getInstance().getUsersAdmin());
		mv.addObject("users", userService.findBy(user));
		mv.addObject("warnMessage", warnMessage);

		return mv;
	}

	@RequestMapping("new-user")
	public ModelAndView newUser() {
		ModelAndView mv = new ModelAndView("users/admin/new-user");
		mv.addObject("navActive", NavIds.getInstance().getUsersAdmin());
		mv.addObject("user", new UserVO(true));

		return mv;
	}

	@RequestMapping(value = "/new-user", method = RequestMethod.POST)
	public String newUserPost(Model model, @ModelAttribute("user") @Validated UserVO user, Errors errors, RedirectAttributes attributes, @RequestParam("imagem") MultipartFile imageFile) {
		try {
			if (errors.hasErrors()) {
				return "users/admin/new-user";
			}

			userService.save(user, imageFile);

			attributes.addFlashAttribute("successMessage", messages.getMessageBy("message.param.created", messages.getMessageBy("label.user")));

		} catch (ImageFormatException e) {
			model.addAttribute("navActive", NavIds.getInstance().getUsersAdmin());
			attributes.addFlashAttribute("errorMessage", messages.getMessageBy("message.invalid.image.format"));

			return "users/admin/new-user";

		} catch (CrudException e) {
			for (Map.Entry<String, String> entry : e.getRejectedValues().entrySet()) {
				errors.rejectValue(entry.getKey(), null, entry.getValue());
			}

			model.addAttribute("navActive", NavIds.getInstance().getUsersAdmin());

			return "users/admin/new-user";

		} catch (Exception e) {
			attributes.addFlashAttribute("errorMessage", messages.getMessageBy("message.sorry.error"));
			model.addAttribute("navActive", NavIds.getInstance().getUsersAdmin());

			return "users/admin/new-user";
		}

		return "redirect:/admin/list-users";
	}

	@RequestMapping(value = "/user/{username}")
	public ModelAndView editUser(@PathVariable("username") User user) {
		ModelAndView mv = new ModelAndView();

		if (user == null) {
			mv.addObject("warnMessage", messages.getMessageBy("message.user.not.found"));
			mv.setViewName("redirect:/admin/list-users");

			return mv;
		}
		if (user.getRoles().contains(RoleEnum.ADMIN) && !user.equals(UserUtils.getUser())) {
			mv.addObject("warnMessage", messages.getMessageBy("message.not.permission.modify.admin"));
			mv.setViewName("users/admin/list-users");

			return mv;
		}

		mv.setViewName("users/admin/list-users");
		mv.addObject(user);

		return mv;
	}

}
