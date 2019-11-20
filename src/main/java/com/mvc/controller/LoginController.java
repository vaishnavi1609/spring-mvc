package com.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@GetMapping(value = { "/"})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcomePage");
		return model;
	}

	@GetMapping(value = { "/homePage"})
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("homepage");
		return model;
	}
	
	@GetMapping(value = { "/errorPage"})
	public ModelAndView errorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errorPage");
		return model;
	}
	
	@RequestMapping(value = "/loginPage",method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out from JournalDEV successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}
	

}
