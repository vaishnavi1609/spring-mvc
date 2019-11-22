package com.mvc.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.mvc.service.LoginAttemptService;

/**
 * Application Lifecycle Listener implementation class AuthenticationFailureListener
 *
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {


	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@EventListener
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		System.out.println("failed");
		WebAuthenticationDetails auth= (WebAuthenticationDetails) event.getAuthentication().getDetails();
		loginAttemptService.loginFailed(auth.getRemoteAddress());
	}

	
	
}
