package com.endProject.footballClubApplication;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Post;
import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.services.CustomUserDetailsService;
import com.endProject.footballClubApplication.services.PlayerService;
import com.endProject.footballClubApplication.services.PostService;
import com.endProject.footballClubApplication.services.RoleService;


@Controller
public class MainPageController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String publicMainPage(Model model) {
		List<Post> posts = postService.findAll();
		if(!posts.isEmpty()) {
			posts.remove(0);
			model.addAttribute("mostMinutes", playerService.mostMinutes());
			model.addAttribute("mostAssists", playerService.mostAssists());
			model.addAttribute("topScorers", playerService.topScorers());
			model.addAttribute("posts", posts);
			model.addAttribute("currentPost", postService.findAll().get(0));
			
			return "index";
		}else {
			model.addAttribute("mostMinutes", null);
			model.addAttribute("mostAssists", null);
			model.addAttribute("topScorers", null);
			return "index"; 
		}
	}
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String mainPage(Model model) {
		List<Post> posts = postService.findAll();
		if(!posts.isEmpty()) {
			posts.remove(0);
			model.addAttribute("mostMinutes", playerService.mostMinutes());
			model.addAttribute("mostAssists", playerService.mostAssists());
			model.addAttribute("topScorers", playerService.topScorers());
			model.addAttribute("posts", posts);
			model.addAttribute("currentPost", postService.findAll().get(0));
			
			return "index";
		}else {
			model.addAttribute("mostMinutes", null);
			model.addAttribute("mostAssists", null);
			model.addAttribute("topScorers", null);
			return "index"; 
		}
	}
	
	
	@RequestMapping(value="/login", method = {RequestMethod.GET})
	public String login() {
		return "login"; 
	}
	
	
	@RequestMapping("/publicPost/{pageNum}")
	public String viewPage(Model model,@PathVariable(name = "pageNum") int pageNum, @Param("keyword") String keyword) {
	     
	    Page<Post> page = postService.listAll(pageNum,keyword);
	     
	    List<Post> listProducts = page.getContent();
	    if (page.getTotalPages() != 0) {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("posts", listProducts);
	    }else {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", 1);
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("posts", listProducts);
	    }
	    
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
	public String updateUser(@Valid User user,@RequestParam("role") Integer roleId, 
			@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		//set user role 
				Optional<Role> role = roleService.finfById(roleId);
				if(role.isPresent()) {
					user.getRoles().add(role.get());
				}
				Optional<User> databaseUser = customUserDetailsService.findById(user.getId());
				if(databaseUser.isPresent()) {
					user.setPassword(databaseUser.get().getPassword());
				}
				customUserDetailsService.save(user,file);
		return "redirect:/userProfile";
	}
	
	@RequestMapping(value="/userProfile/changePassword", method = {RequestMethod.POST, RequestMethod.GET})
	public String changePassword(@RequestParam("password") String newPassword, @RequestParam("id") Integer userId,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("confirmPassword") String confirmPassword,
			RedirectAttributes redirectAttribute) {
		Optional<User> user = customUserDetailsService.findById(userId);
		if(user.isPresent()) {
			
			if (passwordEncoder.matches(oldPassword, user.get().getPassword())) {
				if(newPassword.equals(confirmPassword)) {
					String pwd = passwordEncoder.encode(newPassword);
					User newUser = user.get();
					newUser.setPassword(pwd);
					customUserDetailsService.save(newUser);
				}else {
					redirectAttribute.addFlashAttribute("deleteMessage", "Passwords dont match!!");
					return "redirect:/userProfile";
				}
			}else {
				redirectAttribute.addFlashAttribute("deleteMessage", "Old password dont match!!");
				return "redirect:/userProfile";
			}
		}
		redirectAttribute.addFlashAttribute("successMessage", "Password changed succesfully!!");
		return "redirect:/userProfile";
	}

}
