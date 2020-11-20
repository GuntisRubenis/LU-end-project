package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Post;
import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.RoleRepository;
import com.endProject.footballClubApplication.repositories.UserRepository;
import com.endProject.footballClubApplication.services.CustomUserDetailsService;
import com.endProject.footballClubApplication.services.PostService;
import com.endProject.footballClubApplication.services.RoleService;


@Controller
public class AdminController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	
	@RolesAllowed("ADMIN")
	@RequestMapping("/secure/admin/user")
	public String userPage(Model model) {
		model.addAttribute("users", customUserDetailService.findAll());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("user", new User());
		return "user";
	}
	
	
	@PostMapping("/secure/admin/user/addUser")
	public String addUser (@Valid  User user, Errors errors, @RequestParam("role") Integer roleId,
			@RequestParam("confirmPassword") String confirmPassword, Model model,
			RedirectAttributes redirectAtribute) {
		Optional<Role> role = roleService.finfById(roleId);
		// find role and add it to user
		if(role.isPresent()) {
			user.getRoles().add(role.get());
		}
		//check if username already exists in system
		String username = user.getUsername();
		List<User> users = customUserDetailService.findAll();
		for (User u:users) {
			if (u.getUsername().equals(username)) {
				redirectAtribute.addFlashAttribute("error", "Username already exists, chose diferent one");
				
				return "redirect:/secure/admin/user";
			}
		}
		//get password and check if it equals confirm password
		String password = user.getPassword();
		
		if(password.equals(confirmPassword)) {
			// encripit password before seting it to user
			String encryptPWD = passwordEncoder.encode(password);
			user.setPassword(encryptPWD);
		}else {
			//if passwords dont match return user view with error message
			model.addAttribute("users", customUserDetailService.findAll());
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("password", "Passwords don`t match!!");
			return "user";
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
	public Optional<User> findByIdUser(Integer id) {
		return customUserDetailService.findById(id);
	}
	
	@RequestMapping(value="/secure/admin/user/delete" , method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteUser(Integer id, RedirectAttributes redirectAttribute) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = customUserDetailService.findByUserName(auth.getName());
		List<User> users = customUserDetailService.findAll();
		//check if user is not last administrator in system
		if (id == user.getId() && user.getRoles().get(0).getRole().equals("ADMIN")) {
			int adminCount = 0;
			for (User u :users) {
				if (u.getRoles().get(0).getRole().equals("ADMIN")) {
					adminCount++;
				}
			}
			if (adminCount == 1) {
				redirectAttribute.addFlashAttribute("adminError", "Canot delete this user,because it is last admin!!!");
				return "redirect:/secure/admin/user";
			}
			
		}
		// logout user if he is deleting his profile
		if (id == user.getId()) {
			customUserDetailService.deleteById(id);
			return "redirect:/logout";
		}
		customUserDetailService.deleteById(id);
		return "redirect:/secure/admin/user";
	}
	
	
	@RequestMapping("/secure/admin/post/{pageNum}")
	public String viewPage(Model model,@PathVariable(name = "pageNum") int pageNum) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
	    Page<Post> page = postService.listAll(pageNum);
	     
	    List<Post> listProducts = page.getContent();
	    
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("posts", listProducts);
		    model.addAttribute("user", customUserDetailService.findByUserName(auth.getName()));
	     
	    return "post";
	}
	
	@PostMapping("/secure/admin/post/addPost")
	public String addCoach(Post post, @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		postService.save(post,file);
		redirectAttribute.addFlashAttribute("successMessage", "Post added succesfully!!!");
		return "redirect:/secure/admin/post/1";
	}
	
	@RequestMapping(value="/secure/admin/post/delete" , method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deletePost(Integer id,RedirectAttributes redirectAttribute) {
		postService.deleteById(id);
		redirectAttribute.addFlashAttribute("deleteMessage", "Post deleted succesfully!!!");
		return "redirect:/secure/admin/post/1";
	}
	
	@RequestMapping("/secure/admin/post/findById")
	@ResponseBody
	public Optional<Post> findByIdPost(Integer id) {
		return postService.finfById(id);
	}
	
	@RequestMapping(value="/secure/admin/post/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Post post, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		postService.save(post,file);
		redirectAttribute.addFlashAttribute("successMessage", "Post eddited succesfully!!!");
		return "redirect:/secure/admin/post/1";
	}
	
}
