package com.edylle.pathologicalreports.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.edylle.pathologicalreports.model.constant.NavIds;
import com.edylle.pathologicalreports.model.dto.FindUserDTO;
import com.edylle.pathologicalreports.model.entity.User;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
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
	
	@RequestMapping(value = "/user/{username}")
	public ModelAndView editarEstabelecimento(@PathVariable("username") User user) {
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

		mv.setViewName("users/admin/new-user");
		mv.addObject(user);

		return mv;
	}

}
