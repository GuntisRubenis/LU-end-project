package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.services.PlayerService;
import com.endProject.footballClubApplication.services.TeamService;
import com.endProject.footballClubApplication.services.TrainingService;



@Controller
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private PlayerService playerService;
	
	
	@RequestMapping("/rest/training")
	public String trainingPage (Model model, @Param("keyword") String keyword) {
		List<Training> trainings = trainingService.findAll(keyword);
		model.addAttribute("trainings", trainings);
		model.addAttribute("teams", teamService.findAll());
		return "training";
	}
	
	@PostMapping("/rest/training/addNew")
	public String addTraining(Training training, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		//get team by id, so we can make dir if it not exists
		// and name it like team name, where we will store all team training files
		Optional<Team> team = teamService.finfById(training.getTeamId());
		if(team.isPresent()) {
			trainingService.save(training, file,team.get().getTeamName());
		}
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/training/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTraining(Integer id) {
		// find training by id so we can get name of team and find directory for files
		//then get team name and pass it to training services
		Optional<Training> training = trainingService.finfById(id);
		if(training.isPresent()) {
			trainingService.deleteById(id,training.get().getTeam().getTeamName());
		}
		
		return "redirect:/rest/training";
	}
	
	@RequestMapping("/rest/training/findById") 
	@ResponseBody
	public Optional<Training> findById(Integer id)
	{
		return trainingService.finfById(id);
	}
	
	@RequestMapping("/rest/training/attendance") 
	public String attendance(Integer id, Model model){	
		Optional<Training> training = trainingService.finfById(id);
		if(training.isPresent()) {
			model.addAttribute("players", training.get().getTeam().getPlayers());
			model.addAttribute("training", training.get());
		}
		return "attendance";
	}
	
	
	@RequestMapping(value="/rest/training/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Training training, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		//get team by id, so we can make dir if it not exists
		// and name it like team name, where we will store all team training files
		Optional<Team> team = teamService.finfById(training.getTeamId());
		if(team.isPresent()) {
			trainingService.save(training, file,team.get().getTeamName());
		}
		
		return "redirect:/rest/training";
	}
	
	// from attendanceModal form collect array of playerIds, and training id  
	@PostMapping("/rest/training/addAttendance")
	public String addAttendance (@RequestParam(value="playerId") int[] playersId, @RequestParam(value="id") Integer trainingId ) {
		//find training by id
		Optional<Training> training = trainingService.finfById(trainingId);
		//create new list of players where we will add players who attended training
		ArrayList<Player> players = new ArrayList<Player>();
		// for all players witch ids are present in playerId array we find player by id
		// and if it exists we add it to players list
		for (Integer id:playersId) {
			Optional<Player> player = playerService.finfById(id);
			if(player.isPresent()) {
				players.add(player.get());
			}
		}
		// check if training is present and set trainings players list as our created list 
		if(training.isPresent()) {
			training.get().setPlayers(players);
			// update training in our database
			trainingService.save(training.get());
		}
		
		return "redirect:/rest/training";
	}
	
	
}
