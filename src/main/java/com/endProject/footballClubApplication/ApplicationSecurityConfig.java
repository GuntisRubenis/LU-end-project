package com.endProject.footballClubApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.endProject.footballClubApplication.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	// implement jpa authenticantion with my customized UserDetails Service 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(encodePWd());
	}
	
	// configure http for access, based on user roles or link url
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**").permitAll()
		.and()
		.authorizeRequests()
		.antMatchers("/","/about","/contacts","/publicPost/**").permitAll()
		.antMatchers("/rest/**").hasAnyRole("USER","ADMIN")
		.antMatchers("/secure/**").hasAnyRole("ADMIN").anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login") //lets use custom login page
		.permitAll()
		.and()
		.logout().logoutRequestMatcher(null).logoutSuccessUrl("/")
		.and().csrf().disable();
	}
	
	
	// Password encoder bean to store encoded passwords not plain text
	@Bean 
	public BCryptPasswordEncoder encodePWd () {
		return new BCryptPasswordEncoder();
	}
	
	
}
