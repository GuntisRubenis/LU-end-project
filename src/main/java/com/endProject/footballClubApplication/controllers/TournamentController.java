package com.endProject.footballClubApplication.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.services.TeamService;
import com.endProject.footballClubApplication.services.TournamentService;
@Controller
public class TournamentController {
	
	@Autowired
	private TournamentService tournamentService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping("/rest/tournament")
	public String tournamentPage(Model model, @Param("keyword") String keyword) {
		model.addAttribute("tournaments", tournamentService.findAll(keyword));
		model.addAttribute("teams", teamService.findAll());
		return "tournament";
	}
	
	@RequestMapping("/rest/tournament/addNew")
	public String newTournament(Tournament tournament) {
		tournamentService.save(tournament);
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/tournament/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTournament(Integer id) {
		// find training by id so we can get name of team and find directory for files
				//then get team name and pass it to training services
				Optional<Tournament> tournament = tournamentService.finfById(id);
				if(tournament.isPresent()) {
					tournamentService.deleteById(id);
				}
				return "redirect:/rest/tournament";
	}

}
