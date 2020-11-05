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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String addTeam(Team team, @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		teamService.save(team, file);
		redirectAttribute.addFlashAttribute("successMessage", "Team added succesfully!!!");
		return "redirect:/rest/team";
	}
	
	@RequestMapping(value="/rest/team/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTeam(Integer id,RedirectAttributes redirectAttribute) {
		teamService.deleteById(id);
		redirectAttribute.addFlashAttribute("deleteMessage", "Team deleted succesfully!!!");
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
		//check if team exists, unwrap it and add to model
		if(team.isPresent()) {
			Optional<Coach> coach = coachService.finfById(team.get().getCoachId());
			Optional<Coach> assistantCoach = coachService.finfById(team.get().getAssistantCoachId());
			if (coach.isPresent()) {
				model.addAttribute("coach", coach.get());
				
			}
			if(assistantCoach.isPresent()) {
				model.addAttribute("assistantCoach", assistantCoach.get());
			}
			model.addAttribute("team", team.get());
		}
		return "teamdetails";
	}
	
	@RequestMapping(value="/rest/team/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Team team, @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		teamService.save(team, file);
		redirectAttribute.addFlashAttribute("successMessage", "Team edited succesfully!!!");
		return "redirect:/rest/team";
	}
	
}
