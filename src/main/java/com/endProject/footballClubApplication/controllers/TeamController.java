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
import org.springframework.web.servlet.ModelAndView;

import com.endProject.footballClubApplication.models.Coach;
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
	public String teamPage (Model model, @Param("keyword") String keyword) {
		List<Team> teams = teamService.findAll(keyword);
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
	
	//return view of team details and add team data to it
	@RequestMapping("/rest/team/teamDetails/")
	public String showTeamDetails(Model model,Integer id) {
		Optional<Team> team = teamService.finfById(id);
		Optional<Coach> coach = coachService.finfById(team.get().getCoachId());
		Optional<Coach> assistantCoach = coachService.finfById(team.get().getAssistantCoachId());
		//check if team exists, unwrap it and add to model
		if(team.isPresent()) {
			model.addAttribute("team", team.get());
			model.addAttribute("coach", coach.get());
			model.addAttribute("assistantCoach", assistantCoach.get());
		}
		return "teamdetails";
	}
	
	@RequestMapping(value="/rest/team/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Team team, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		teamService.save(team, file);
		return "redirect:/rest/team";
	}
	
}
