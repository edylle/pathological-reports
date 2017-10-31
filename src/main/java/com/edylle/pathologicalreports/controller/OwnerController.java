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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.edylle.pathologicalreports.exception.CrudException;
import com.edylle.pathologicalreports.model.constant.NavIds;
import com.edylle.pathologicalreports.model.dto.FindOwnerDTO;
import com.edylle.pathologicalreports.model.entity.Owner;
import com.edylle.pathologicalreports.service.OwnerService;
import com.edylle.pathologicalreports.utils.Messages;

@Controller
@RequestMapping("/owner")
public class OwnerController {

	@Autowired
	private Messages messages;
	@Autowired
	private OwnerService ownerService;

	@ModelAttribute("getNavIds")
	public NavIds getNavIds() {
		return NavIds.getInstance();
	}

	@RequestMapping("list-owners")
	public ModelAndView findUsers(@ModelAttribute("owner") FindOwnerDTO owner,
			                      @RequestParam(name = "initialPage", value = "", required = false) String initialPage) {

		ModelAndView mv = new ModelAndView("users/list-owners");
		mv.addObject("navActive", NavIds.getInstance().getOwners());

		int page = 0;
		int size = 5;

		if (StringUtils.isNumeric(initialPage)) {
			page = Integer.valueOf(initialPage);
		}

		mv.addObject("owners", ownerService.findBy(owner, page, size));

		return mv;
	}

	@RequestMapping("new-owner")
	public ModelAndView newOwner() {
		ModelAndView mv = new ModelAndView("users/new-owner");
		mv.addObject("navActive", NavIds.getInstance().getOwners());
		mv.addObject("owner", new Owner(true));

		return mv;
	}

	@RequestMapping(value = "/new-owner", method = RequestMethod.POST)
	public String newOwnerPost(Model model, @ModelAttribute("owner") @Validated Owner owner, Errors errors, RedirectAttributes attributes) {
		try {
			if (errors.hasErrors()) {
				model.addAttribute("navActive", NavIds.getInstance().getUsersProfessor());
				return "users/new-owner";
			}

			ownerService.save(owner);

			String message = owner.isNewRegistry() ? "message.param.created" : "message.param.updated";
			attributes.addFlashAttribute("successMessage", messages.getMessageBy(message, messages.getMessageBy("label.owner")));

			return "redirect:/owner/list-owners";

		} catch (CrudException e) {
			for (Map.Entry<String, String> entry : e.getRejectedValues().entrySet()) {
				errors.rejectValue(entry.getKey(), null, entry.getValue());
			}

			model.addAttribute("navActive", NavIds.getInstance().getOwners());

			return "users/new-owner";

		} catch (Exception e) {
			model.addAttribute("errorMessage", messages.getMessageBy("message.sorry.error"));
			model.addAttribute("navActive", NavIds.getInstance().getOwners());

			return "users/new-owner";
		}
	}

	@RequestMapping(value = "/owner/{id}")
	public ModelAndView viewOwner(@PathVariable("id") Owner owner) {
		ModelAndView mv = new ModelAndView();

		if (owner == null) {
			mv.addObject("warnMessage", messages.getMessageBy("message.owner.not.found"));
			mv.setViewName("redirect:/owner/list-owners");

			return mv;
		}

		mv.addObject("navActive", NavIds.getInstance().getOwners());
		mv.setViewName("users/new-owner");
		mv.addObject("owner", owner);

		return mv;
	}
}
