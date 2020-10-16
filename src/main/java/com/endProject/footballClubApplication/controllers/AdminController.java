package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
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
		return "admin";
	}
	
	
	@PostMapping("/secure/admin/user/addUser")
	public String addUser (User user, @RequestParam("role") Integer roleId,@RequestParam("confirmPassword") String confirmPassword ) {
		Optional<Role> role = roleService.finfById(roleId);
		if(role.isPresent()) {
			user.getRoles().add(role.get());
		}
		String password = user.getPassword();
		
		if(password.equals(confirmPassword)) {
			String encryptPWD = passwordEncoder.encode(password);
			user.setPassword(encryptPWD);
		}else {
			throw new IllegalArgumentException("password dont match");
		}
		
		customUserDetailService.save(user);
		return "redirect:";
	}
	
	@RequestMapping(value="/secure/admin/user/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String updateUser(User user, @RequestParam("confirmPassword") String confirmPassword, 
			@RequestParam("id") Integer id, @RequestParam("oldPassword") String oldPassword,@RequestParam("role") Integer roleId ){
		//set user role 
		Optional<Role> role = roleService.finfById(roleId);
		if(role.isPresent()) {
			user.getRoles().add(role.get());
		}
		//check if old password is entered it means admin wants to change password
		if(oldPassword.isEmpty()) {
			// get user from database and set password to database password
			Optional<User> oldUser = customUserDetailService.findById(id);
			if(oldUser.isPresent()) {
				user.setPassword(oldUser.get().getPassword());
			}
			customUserDetailService.save(user);
		}else {
			// get user from database to get old password	
			Optional<User> oldUser = customUserDetailService.findById(id);
				if(oldUser.isPresent()) {
					//get old password
					String oldPWD = oldUser.get().getPassword();
					//check if database password matched typed old password
					if(passwordEncoder.matches(oldPassword,oldPWD)) {
						//check if new password is typed correctly
						if(user.getPassword().equals(confirmPassword)) {
							// encrypt new password and add it to user, save user to database
							String encryptPWD = passwordEncoder.encode(user.getPassword());
							user.setPassword(encryptPWD);
							customUserDetailService.save(user);
						}
						else {
							throw new IllegalArgumentException("new passwrod dont match!!");
						}
					}
					else {
						throw new IllegalArgumentException("old passwrod dont match!!");
					}
				}	
		}
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
