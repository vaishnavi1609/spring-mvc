package com.mvc.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureListener.class);
	
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		LOGGER.info("AuthenticationFailureBadCredentialsEvent occured");
		loginAttemptService.loginFailed(event.getAuthentication().getPrincipal().toString());
	}


	
}
