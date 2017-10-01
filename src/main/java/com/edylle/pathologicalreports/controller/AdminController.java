package com.edylle.pathologicalreports.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edylle.pathologicalreports.model.constant.NavIds;
import com.edylle.pathologicalreports.model.dto.FindUserDTO;
import com.edylle.pathologicalreports.model.enumeration.RoleEnum;
import com.edylle.pathologicalreports.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@ModelAttribute("getRoles")
	public List<RoleEnum> getAllRoles() {
		return Arrays.asList(RoleEnum.PROFESSOR, RoleEnum.STUDENT);
	}

	@ModelAttribute("getNavIds")
	public NavIds getNavIds() {
		return NavIds.getInstance();
	}

	@RequestMapping("find-users")
	public ModelAndView findUsers(@ModelAttribute("user") FindUserDTO user) {
		ModelAndView mv = new ModelAndView("users/admin/users");
		mv.addObject("navActive", NavIds.getInstance().getUsersAdmin());
		mv.addObject("users", userService.findBy(user));

		return mv;
	}

}
