package com.endProject.footballClubApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainPageController {

	
	
	@RequestMapping("/")
	public String mainPage(Model model) {
		
		return "index"; 
	}
	
	@RequestMapping("/contacts")
	public String contactsPage(Model model) {
		return "contacts"; 
	}
	
	@RequestMapping("/about")
	public String aboutPage(Model model) {
		return "about"; 
	}

}
