package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.services.CoachService;
import com.endProject.footballClubApplication.services.TeamService;



@Controller
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	@Autowired
	private CoachService coachService;
	
	@RequestMapping("/rest/team")
	public String teamPage (Model model) {
		List<Team> teams = teamService.findAll();
		model.addAttribute("teams", teams);
		model.addAttribute("coaches", coachService.findAll());
		return "team";
	}
	
	@PostMapping("/rest/team/addNew")
	public String addTeam(Team team, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		teamService.save(team, file);
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/team/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTeam(Integer id) {
		teamService.deleteById(id);
		return "redirect:/rest/team";
	}
	
	@RequestMapping("/rest/team/findById") 
	@ResponseBody
	public Optional<Team> findById(Integer id)
	{
		return teamService.finfById(id);
	}
	
	@RequestMapping(value="/rest/team/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(Team team, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		teamService.save(team, file);
		return "redirect:/rest/team";
	}
	
}
