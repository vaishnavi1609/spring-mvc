package com.mvc.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.mvc.service.LoginAttemptService;

/**
 * Application Lifecycle Listener implementation class AuthenticationSuccessListener
 *
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {

	@Autowired
	LoginAttemptService loginAttemptService;
	
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		WebAuthenticationDetails auth= (WebAuthenticationDetails) event.getAuthentication().getDetails();
		loginAttemptService.loginSucceeded(auth.getRemoteAddress());
	}

	
	
}
