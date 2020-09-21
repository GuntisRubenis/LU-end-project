package com.endProject.footballClubApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.UserRepository;


@Controller
@RequestMapping("/secure")
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("admin")
	public String adminPage() {
		return "admin";
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping("admin/adduser")
	public String addUser (@RequestBody User user) {
		String password = user.getPassword();
		String encryptPWD = passwordEncoder.encode(password);
		user.setPassword(encryptPWD);
		userRepository.save(user);
		return "admin";
	}

}
