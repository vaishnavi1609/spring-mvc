package com.mvc.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.mvc.service.LoginAttemptService;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler
{

	@Autowired
	LoginAttemptService loginAttemptService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationFailureHandler.class);
	
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if(loginAttemptService.isBlocked(request.getParameter("username")))
		{
			LOGGER.debug("redirecting to blocked page");
			response.sendRedirect("blockedPage");
		}
		else {
			LOGGER.debug("redirecting to login page");
			response.sendRedirect("loginPage?error");
		}
	}
	
}