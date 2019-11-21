package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mvc.service.ServiceClass;

@Controller
public class LoginController {
	
	@Autowired
	ServiceClass service;

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
		service.display();
		return model;
	}
	
	@GetMapping(value = { "/errorPage"})
	public ModelAndView errorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errorPage");
		return model;
	}
	@GetMapping(value = { "/admin"})
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("admin");
		return model;
	}
	
	@GetMapping(value = "/loginPage")
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addObject("message", "Logged out successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}
	
	

}
