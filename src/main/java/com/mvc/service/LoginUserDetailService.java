package com.mvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	public UserDetails loadUserByUsername(String username) {

		if(loginAttemptService.isBlocked(username))
		{
			throw new RuntimeException();
		}		
		Optional<User> optUser=userRepository.findById(username);
		User user=null;
		if(optUser.isPresent())
			user=optUser.get();
		else
			throw new UsernameNotFoundException("User not found");
		
		List<String> list=user.getRoles().stream().map(Role::getRoles).collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(
	              user.getUsername(), user.getPassword(),getAuthorities(list));
	}
	
	private List<GrantedAuthority> getAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
