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

import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.services.PlayerService;



@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	
	@RequestMapping("/rest/player")
	public String playerPage (Model model) {
		List<Player> players = playerService.findAll();
		model.addAttribute("players", players);
		return "player";
	}
	
	@PostMapping("/rest/player/addNew")
	public String addPlayer(Player player) {
		playerService.save(player);
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/player/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deletePlayer(Integer id) {
		playerService.deleteById(id);
		return "redirect:/rest/player";
	}
	
	@RequestMapping("/rest/player/findById") 
	@ResponseBody
	public Optional<Player> findById(Integer id)
	{
		return playerService.finfById(id);
	}
	
	@RequestMapping(value="/rest/player/update", method = {RequestMethod.PUT, RequestMethod.GET})
	public String update(Player player) {
		playerService.save(player);
		return "redirect:/rest/player";
	}

}
