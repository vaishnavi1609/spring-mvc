package com.mvc.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
				
		authenticationMgr
				.userDetailsService(userDetailsService)
				.passwordEncoder(getPasswordEncoder());
		
		
/*		authenticationMgr
		.jdbcAuthentication().passwordEncoder(getPasswordEncoder())
			.dataSource(dataSource)
			  .usersByUsernameQuery(
					   "select username,password, enabled from users where username=?")
					  .authoritiesByUsernameQuery(
					   "select username, role from user_roles where username=?"
*/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/homePage").access("hasRole('ROLE_USER')")
			.antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
			.and()
				.formLogin().loginPage("/loginPage").failureHandler(authenticationFailureHandler())
				.defaultSuccessUrl("/homePage")
				.usernameParameter("username").passwordParameter("password")
			   .and()
			   .exceptionHandling().accessDeniedPage("/errorPage")
			   .and()
			   .logout().logoutSuccessUrl("/loginPage?logout").invalidateHttpSession(true)
			   .and()
			   .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(10);
		
	}
	
	 @Bean
	  public AuthenticationFailureHandler authenticationFailureHandler()
	  {
	    return new RestAuthenticationFailureHandler();
	  }
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
	    return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }
	
}