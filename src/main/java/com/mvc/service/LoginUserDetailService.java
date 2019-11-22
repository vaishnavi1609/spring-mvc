package com.mvc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mvc.entity.Role;
import com.mvc.entity.User;
import com.mvc.repository.RoleRepository;
import com.mvc.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class LoginUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	LoginAttemptService loginAttemptService;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String ip = getUserIP();
		System.out.println(loginAttemptService.isBlocked(ip));
		if(loginAttemptService.isBlocked(ip))
		{
			System.out.println("blocked");
			throw new RuntimeException("blocked");
		}
	
		
		User user=userRepository.findById(username).get();
		if(user==null)
			return new org.springframework.security.core.userdetails.User("","",null);
		
		List<String> list=roleRepository.getRoleByUser(user).stream().map(x ->x.getRole()).collect(Collectors.toList());
		System.out.println(list);
		
		return new org.springframework.security.core.userdetails.User(
	              user.getUsername(), user.getPassword(),getAuthorities(list));
	}
	public String getUserIP() {
		  HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();;
		String xfHeader = request.getHeader("X-Forwarded-For");
		
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    return xfHeader.split(",")[0];
	}
	private List<GrantedAuthority> getAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
