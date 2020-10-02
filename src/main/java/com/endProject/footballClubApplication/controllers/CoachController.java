package com.endProject.footballClubApplication.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.services.CoachService;



@Controller
public class CoachController {
	
	@Autowired
	private CoachService coachService;
	
	
	// display all coaches, keyword is for search function, so we can filter coaches
	@RequestMapping("/rest/coach")
	public String coachPage (Model model, @Param("keyword") String keyword) {
		List<Coach> coaches = coachService.findAll(keyword);
		model.addAttribute("coaches", coaches);
		return "coach";
	}
	
	@PostMapping("/rest/coach/addNew")
	public String addCoach(Coach coach) {
		coachService.save(coach);
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/coach/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteCoach(Integer id) {
		coachService.deleteByid(id);
		return "redirect:/rest/coach";
	}
	
	@RequestMapping("/rest/coach/findById") 
	@ResponseBody
	public Optional<Coach> findById(Integer id)
	{
		return coachService.finfById(id);
	}
	
	@RequestMapping(value="/rest/coach/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(Coach coach) {
		coachService.save(coach);
		return "redirect:/rest/coach";
	}
	
	//
	@RequestMapping(value="/rest/coach/upload/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String  uploadFile(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		
		coachService.uploadFile(id,file);
		
		return"redirect:/rest/coach";
	}


}
