package com.endProject.footballClubApplication.controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Statistics;
import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.services.PlayerService;
import com.endProject.footballClubApplication.services.StatisticService;
import com.endProject.footballClubApplication.services.TeamService;
import com.endProject.footballClubApplication.services.TournamentService;
@Controller
public class TournamentController {
	
	@Autowired
	private TournamentService tournamentService;
	@Autowired
	private TeamService teamService;
	@Autowired
	private PlayerService playerService;
	@Autowired
	private StatisticService statisticService;
	
	@RequestMapping("/rest/tournament/{pageNum}")
	public String viewPage(Model model,@PathVariable(name = "pageNum") int pageNum, @Param("keyword") String keyword) {
	     
	    Page<Tournament> page = tournamentService.listAll(pageNum,keyword);
	     
	    List<Tournament> listProducts = page.getContent();
	    if (page.getTotalPages() != 0) {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("tournaments", listProducts);
		    model.addAttribute("teams", teamService.findAll());
	    }else {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", 1);
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("tournaments", listProducts);
		    model.addAttribute("teams", teamService.findAll());
	    }
	    
	     
	    return "tournament";
	}
	
	
	@RequestMapping("/rest/tournament/addNew")
	public String newTournament(Tournament tournament, RedirectAttributes redirectAttribute) {
		tournamentService.save(tournament);
		redirectAttribute.addFlashAttribute("successMessage", "Tournament added succesfully!!!");
		return "redirect:/rest/tournament/1";
	}
	
	@RequestMapping(value="/rest/tournament/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTournament(Integer id, RedirectAttributes redirectAttribute) {
		tournamentService.deleteById(id);
		redirectAttribute.addFlashAttribute("deleteMessage", "Tournament deleted succesfully!!!");
		return "redirect:/rest/tournament/1";
	}
	
	@RequestMapping("/rest/tournament/findById")
	@ResponseBody
	public Optional<Tournament> findByid(Integer id) {
		return tournamentService.finfById(id);
	}
	
	@RequestMapping(value="/rest/tournament/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Tournament tournament, RedirectAttributes redirectAttribute){
		tournamentService.save(tournament);
		redirectAttribute.addFlashAttribute("successMessage", "Tournament edited succesfully!!!");
		return "redirect:/rest/tournament/1";
	}
	
	// return attendance page with list of current teams players
		// evaluates if player  already attended tournament so we can check checkbox in attendance.html
		@RequestMapping("/rest/tournament/report") 
		public String report(Model model, Integer id){
			Optional<Tournament> tournament = tournamentService.finfById(id);
			if(tournament.isPresent()) {
				//store all team players in a map and give value to false
				HashMap<Player, Boolean> players = new HashMap<Player, Boolean>();
				for(Player player : tournament.get().getTeam().getPlayers()) {
					players.put(player, false);
				}
				// loop throght all team players and tournament players
				for (Player player: players.keySet()){
		            for (Player p: tournament.get().getPlayers()) {
		            	//check if player from team was already in tournament players
		            	// if id`s matches set value to true
		            	if (player.getId()==p.getId()) {
		            		players.put(player, true);
		            	}
		            }  
				} 
				model.addAttribute("tournament", tournament.get());
				model.addAttribute("players", players);
				model.addAttribute("tournamentPlayers", tournament.get().getPlayers());
				
			}
			return "tournamentReport";
		}
		
		// from attendanceModal form collect array of playerIds, and tournament id  
		@PostMapping("/rest/tournament/report/addPlayers")
		public String addAttendance (@RequestParam(value="playerId", required = false, defaultValue = "0") int[] playersId, 
				@RequestParam(value="id") Integer tournamentId,
				RedirectAttributes redirectAttribute) {
					//find tournament by id
					Optional<Tournament> tournament = tournamentService.finfById(tournamentId);
					//create new list of players where we will add players who attended tournament
					ArrayList<Player> players = new ArrayList<Player>();
					// for all players witch id`s are present in playerId array we find player by id
					// and if it exists we add it to players list
					for (Integer id:playersId) {
						Optional<Player> player = playerService.finfById(id);
						if(player.isPresent()) {
							players.add(player.get());
						}
					}
					// check if tournament is present and set tournaments players list as our created list 
					if(tournament.isPresent()) {
						Tournament updatedTournament = tournament.get();
						updatedTournament.setPlayers(players);
						// update tournament in our database
						tournamentService.save(updatedTournament);
						redirectAttribute.addFlashAttribute("atendSuccessMessage", "Attendance added succesfully!!!");
					}
					
					
					return "redirect:/rest/tournament/report/?id="+tournamentId;
				}
				//returns arrays of statistic values
				@PostMapping("/rest/tournament/report/addStatistic")
				public String addStatistics(
						@RequestParam(value="tournamentId") Integer tournamentId,
						@RequestParam(value="playerId") int[] playerIds,
						@RequestParam(value="goals") int[] goals,
						@RequestParam(value="assists") int[] assists,
						@RequestParam(value="minutes") int[] minutes,
						RedirectAttributes redirectAttribute)
				{
							Optional<Tournament> tournament = tournamentService.finfById(tournamentId);
							if(tournament.isPresent()) {
								//get list of tournament statistics
								List<Statistics> statList = tournament.get().getStatictics();
								// if statistic list is empty, then add new statistic one by one
								if(statList.isEmpty()) {
									//for each player set individual statistic 
									for (Integer i=0; i<playerIds.length; i++) {
										Statistics statistic = new Statistics();
										statistic.setTournamentId(tournamentId);
										statistic.setPlayerId(playerIds[i]);
										statistic.setGoals(goals[i]);
										statistic.setAssists(assists[i]);
										statistic.setMinutes(minutes[i]);
										statisticService.save(statistic);
										redirectAttribute.addFlashAttribute("statSuccessMessage", "Statistic added succesfully!!!");
									}
								}
								// if its not empty loop throught each statistic and delete it 
								if(!statList.isEmpty()) {
									for(Integer i=0; i<statList.size();i++) {
										Statistics stat = tournament.get().getStatictics().get(i);
										statisticService.deleteByid(stat.getId());
									}
									// add new statistics one by one
									for (Integer i=0; i<playerIds.length; i++) {
										Statistics statistic = new Statistics();
										statistic.setTournamentId(tournamentId);
										statistic.setPlayerId(playerIds[i]);
										statistic.setGoals(goals[i]);
										statistic.setAssists(assists[i]);
										statistic.setMinutes(minutes[i]);
										statisticService.save(statistic);
										redirectAttribute.addFlashAttribute("statSuccessMessage", "Statistic added succesfully!!!");
									}
								}
							}
					 return "redirect:/rest/tournament/report/?id="+tournamentId;
				}
				
		@RequestMapping("/rest/tournament/tournamentDetails")
		public String tournamentDetails(Model model, Integer tournamentId) {
			Optional<Tournament> tournament = tournamentService.finfById(tournamentId);
			if(tournament.isPresent()) {
				model.addAttribute("tournament", tournament.get());
				model.addAttribute("players", tournament.get().getPlayers());
				model.addAttribute("team", tournament.get().getTeam());
			}
			
			return "tournamentDetails";		
		}
		

}
