package com.mvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mvc.entity.Role;
import com.mvc.entity.User;
import com.mvc.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class LoginUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	LoginAttemptService loginAttemptService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginUserDetailService.class);

	public UserDetails loadUserByUsername(String username) {

		if(loginAttemptService.isBlocked(username))
		{
			LOGGER.error("USER BLOCKED!!");
			throw new RuntimeException();
		}		
		Optional<User> optUser=userRepository.findById(username);
		User user=null;
		if(optUser.isPresent())
			user=optUser.get();
		else
		{
			LOGGER.debug("user with username {} not found",username);
			throw new UsernameNotFoundException("User not found");
		}
		
		List<String> list=user.getRoles().stream().map(Role::getRoles).collect(Collectors.toList());
		
		String encodedPassword=passwordEncoder.encode(user.getPassword());
		return new org.springframework.security.core.userdetails.User(
	              user.getUsername(), encodedPassword,getAuthorities(list));
	}
	
	private List<GrantedAuthority> getAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        LOGGER.debug("getting authorities for user");
        return authorities;
    }
}
