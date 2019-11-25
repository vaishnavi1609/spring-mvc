package com.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


	@GetMapping(value = { "/"})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("welcomePage");
		LOGGER.debug("view set to welcome page");
		return model;
	}

	@GetMapping(value = { "/homePage"})
	public ModelAndView homePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("homepage");
		LOGGER.debug("view set to home page");
		service.display();
		return model;
	}
	
	@GetMapping(value = { "/blockedPage"})
	public ModelAndView blockedPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("blockedPage");
		LOGGER.debug("view set to block page");
		return model;
	}
	@GetMapping(value = { "/errorPage"})
	public ModelAndView errorPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("errorPage");
		LOGGER.debug("view set to error page");
		return model;
	}
	@GetMapping(value = { "/admin"})
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("admin");
		LOGGER.debug("view set to admin page");
		return model;
	}
	
	@GetMapping(value = "/loginPage")
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			LOGGER.debug("wrong credentials entered");
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			LOGGER.debug("logging out");
			model.addObject("message", "Logged out successfully.");
		}

		model.setViewName("loginPage");
		return model;
	}
	
	

}
