package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.RoleRepository;
import com.endProject.footballClubApplication.repositories.UserRepository;
import com.endProject.footballClubApplication.services.CustomUserDetailsService;
import com.endProject.footballClubApplication.services.RoleService;


@Controller
public class AdminController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RolesAllowed("ADMIN")
	@RequestMapping("/secure/admin/user")
	public String userPage(Model model) {
		model.addAttribute("users", customUserDetailService.findAll());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("user", new User());
		return "admin";
	}
	
	
	@PostMapping("/secure/admin/user/addUser")
	public String addUser (@Valid  User user, Errors errors, @RequestParam("role") Integer roleId,
			@RequestParam("confirmPassword") String confirmPassword, Model model ) {
		Optional<Role> role = roleService.finfById(roleId);

		if(role.isPresent()) {
			user.getRoles().add(role.get());
		}
		String password = user.getPassword();
		
		if(password.equals(confirmPassword)) {
			String encryptPWD = passwordEncoder.encode(password);
			user.setPassword(encryptPWD);
		}else {
			model.addAttribute("users", customUserDetailService.findAll());
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("password", "Passwords don`t match!!");
			return "admin";
		}
		
		customUserDetailService.save(user);
		return "redirect:";
	}
	
	@RequestMapping(value="/secure/admin/user/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String updateUser(@Valid User user,@RequestParam("role") Integer roleId) {
		//set user role 
				Optional<Role> role = roleService.finfById(roleId);
				if(role.isPresent()) {
					user.getRoles().add(role.get());
				}
				Optional<User> databaseUser = customUserDetailService.findById(user.getId());
				if(databaseUser.isPresent()) {
					user.setPassword(databaseUser.get().getPassword());
				}
				customUserDetailService.save(user);
		return "redirect:/secure/admin/user";
	}
	
	@RequestMapping("/secure/admin/user/findById")
	@ResponseBody
	public Optional<User> findById(Integer id) {
		return customUserDetailService.findById(id);
	}
	
	@RequestMapping(value="/secure/admin/user/delete" , method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteUser(Integer id) {
		customUserDetailService.deleteById(id);
		return "redirect:/secure/admin/user";
	}
	
	
	@RequestMapping("/secure/admin/post")
	public String postPage(Model model) {
		return "post";
	}
	
}
