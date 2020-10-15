package com.endProject.footballClubApplication.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.RoleRepository;
import com.endProject.footballClubApplication.repositories.UserRepository;
import com.endProject.footballClubApplication.services.CustomUserDetailsService;
import com.endProject.footballClubApplication.services.RoleService;


@Controller
public class AdminController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RolesAllowed("ADMIN")
	@RequestMapping("/secure/admin/user")
	public String adminPage(Model model) {
		model.addAttribute("users", customUserDetailService.findAll());
		model.addAttribute("roles", roleService.findAll());
		return "admin";
	}
	
	
	@PostMapping("/secure/admin/user/addUser")
	public String addUser (User user, @RequestParam("role") Integer roleId) {
		Optional<Role> role = roleService.finfById(roleId);
		if(role.isPresent()) {
			user.getRoles().add(role.get());
		}
		String password = user.getPassword();
		String encryptPWD = passwordEncoder.encode(password);
		user.setPassword(encryptPWD);
		userRepository.save(user);
		return "redirect:";
	}
	
	@RequestMapping("/secure/admin/findById")
	@ResponseBody
	public Optional<User> findById(Integer id) {
		return customUserDetailService.findById(id);
	}
	
	@RequestMapping(value="/secure/admin/delete" , method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteUser(Integer id) {
		customUserDetailService.deleteById(id);
		return "redirect:/secure/admin/user";
	}
	
}
