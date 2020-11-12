package com.endProject.footballClubApplication;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.endProject.footballClubApplication.models.Post;
import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.services.CustomUserDetailsService;
import com.endProject.footballClubApplication.services.PostService;
import com.endProject.footballClubApplication.services.RoleService;


@Controller
public class MainPageController {
	
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String mainPage(Model model) {
		return "index"; 
	}
	
	@RequestMapping(value="/login", method = {RequestMethod.GET})
	public String login() {
		return "login"; 
	}
	
	@RequestMapping("/publicPost")
	public String postPage(Model model, @Param("keyword") String keyword) {
		model.addAttribute("posts", postService.findAll(keyword));
		return "publicPosts"; 
	}
	
	@RequestMapping("/publicPost/postDetails")
	public String postDetailPage(Model model, @RequestParam("id") Integer id) {
		Optional<Post> post = postService.finfById(id);
		if (post.isPresent()) {
			model.addAttribute("post", post.get());	
		}
		return "postDetails"; 
	}
	
	@RequestMapping("/contacts")
	public String contactsPage(Model model) {
		return "contacts"; 
	}
	
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		return "about"; 
	}
	
	@RequestMapping("/userProfile")
	public String userProfile(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user", customUserDetailsService.findByUserName(auth.getName()));
		model.addAttribute("roles", roleService.findAll());
		return "userProfile"; 
	}
	
	@RequestMapping(value="/userProfile/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String updateUser(@Valid User user,@RequestParam("role") Integer roleId) {
		//set user role 
				Optional<Role> role = roleService.finfById(roleId);
				if(role.isPresent()) {
					user.getRoles().add(role.get());
				}
				Optional<User> databaseUser = customUserDetailsService.findById(user.getId());
				if(databaseUser.isPresent()) {
					user.setPassword(databaseUser.get().getPassword());
				}
				customUserDetailsService.save(user);
		return "redirect:/userProfile";
	}
	
	@RequestMapping(value="/userProfile/changePassword", method = {RequestMethod.POST, RequestMethod.GET})
	public String changePassword(@RequestParam("password") String newPassword, @RequestParam("id") Integer userId,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		Optional<User> user = customUserDetailsService.findById(userId);
		if(user.isPresent()) {
			
			if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
				if(newPassword.equals(confirmPassword)) {
					String pwd = passwordEncoder.encode(newPassword);
					User newUser = user.get();
					newUser.setPassword(pwd);
					customUserDetailsService.save(newUser);
				}else {
					System.out.println("Password dont match");
				}
			}else {
				System.out.println("Old pwd dont match");
			}
		}
		
		return "redirect:/userProfile";
	}

}
