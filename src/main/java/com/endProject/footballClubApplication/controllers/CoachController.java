package com.endProject.footballClubApplication.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.services.CoachService;



@Controller
public class CoachController {
	
	@Autowired
	private CoachService coachService;
	
	@RequestMapping("/rest/coach")
	public String coachPage (Model model) {
		List<Coach> coaches = coachService.findAll();
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

}
