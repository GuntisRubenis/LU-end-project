package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Team;
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
		tournamentService.deleteById(id);
		return "redirect:/rest/tournament";
	}
	
	@RequestMapping("/rest/tournament/findById")
	@ResponseBody
	public Optional<Tournament> findByid(Integer id) {
		return tournamentService.finfById(id);
	}
	
	@RequestMapping(value="/rest/tournament/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Tournament tournament){
		tournamentService.save(tournament);
		return "redirect:/rest/tournament";
	}

}
