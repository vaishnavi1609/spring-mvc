package com.mvc.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

	//@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	public String display() {
		return "hello world";
	}
}
