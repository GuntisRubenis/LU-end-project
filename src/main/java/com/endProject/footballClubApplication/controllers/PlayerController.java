package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.services.PlayerService;
import com.endProject.footballClubApplication.services.TeamService;



@Controller
public class PlayerController {
	
	@Autowired
	private PlayerService playerService;
	@Autowired
	private TeamService teamService;
	
	@RequestMapping("/rest/player/{pageNum}")
	public String viewPage(Model model,@PathVariable(name = "pageNum") int pageNum, @Param("keyword") String keyword) {
	     
	    Page<Player> page = playerService.listAll(pageNum,keyword);
	     
	    List<Player> listProducts = page.getContent();
	    if (page.getTotalPages() != 0) {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("players", listProducts);
		    model.addAttribute("teams", teamService.findAll());
	    }else {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", 1);
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("players", listProducts);
		    model.addAttribute("teams", teamService.findAll());
	    }
	    
	     
	    return "player";
	}
	
	
	@PostMapping("/rest/player/addNew")
	public String addPlayer(Player player, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		playerService.save(player, file);
		redirectAttribute.addFlashAttribute("successMessage", "Player added succesfully!!!");
		return "redirect:/rest/player/1";
	}
	
	@RequestMapping(value="/rest/player/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deletePlayer(Integer id, RedirectAttributes redirectAttribute) {
		playerService.deleteById(id);
		redirectAttribute.addFlashAttribute("deleteMessage", "Player deleted succesfully!!!");
		return "redirect:/rest/player/1";
	}
	
	@RequestMapping("/rest/player/findById") 
	@ResponseBody
	public Optional<Player> findById(Integer id)
	{
		return playerService.finfById(id);
	}
	
	@RequestMapping(value="/rest/player/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Player player, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		playerService.save(player, file);
		redirectAttribute.addFlashAttribute("successMessage", "Player edited succesfully!!!");
		return "redirect:/rest/player/1";
	}
	
	@RequestMapping(value="/rest/player/playerDetails")
	public String playerDetails (Model model, Integer id) {
		Optional<Player> player = playerService.finfById(id);
		
		if (player.isPresent()) {
			
			model.addAttribute("player", player.get());
			Team team =player.get().getTeam();
			if(team != null) {
				model.addAttribute("team", team);
			}
			
			
		}
		
		return "playerDetails";
	}
	

}
