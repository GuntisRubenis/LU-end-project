package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.repositories.PlayerRepository;



@Service 
public class PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\players\\";
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\players\\";
	public void save(Player player,  MultipartFile file) throws IllegalStateException, IOException {
		 playerRepository.save(player);
		 
		 if(!file.isEmpty()) {
			 File newDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 file.transferTo(new File(PATH+player.getId()+".jpg"));
		 }
	}
	
	public void save(Player player){
		
		playerRepository.save(player);
	}
	
	public void deleteById(Integer id) {
		//check if file exists in our directory and delete it
		File file = new File(PATH+id+".jpg");
		 if(file.exists()) {
			 file.delete();
		 }
		 playerRepository.deleteById(id);
	}
	
	public Page<Player> listAll(int pageNum, String keyword) {
	    int pageSize = 5;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    if(keyword != null) {
	    	return playerRepository.findAll(keyword, pageable);
	    } 
	    return playerRepository.findAll(pageable);
	}
	
	public Optional<Player> finfById(Integer id){
		return playerRepository.findById(id);
	}
	
	public List<Player> findAll(){
		return playerRepository.findAll();
	}
	
	// get top scorers
	public List<Player> topScorers(){
		//return this list with 3 top scorers
		List<Player> topScorers = new ArrayList<Player>();
		// get all players
		List<Player> players = playerRepository.findAll();
		
		for(int i=0; i<=2; i++) {
			Player highestScorer = new Player();
			// find first pivot element and set it as highest scorer
			if(players.get(0).getAllGoals()>=players.get(1).getAllGoals()) {
				highestScorer=players.get(0);
				
			}else {
				highestScorer=players.get(1);
				
			}
			// go throught all players and find highest scorer
			for(Player p:players) {
				if(p.getAllGoals()>highestScorer.getAllGoals() && !p.equals(highestScorer)) {
					highestScorer=p;
				}
			}
			// add highest scorer to list and remove it from all players list
			topScorers.add(highestScorer);
			players.remove(highestScorer);
			
		}
		
		return topScorers;
	}
	
	public List<Player> mostAssists(){
		List<Player> topScorers = new ArrayList<Player>();
		List<Player> players = playerRepository.findAll();
		Player highestScorer = new Player();
		for(int i=0; i<=2; i++) {
			if(players.get(0).getAllAssists()>=players.get(1).getAllAssists()) {
				highestScorer=players.get(0);
				
			}else {
				highestScorer=players.get(1);
				
			}
			for(Player p:players) {
				if(p.getAllAssists()>highestScorer.getAllAssists() && !p.equals(highestScorer)) {
					highestScorer=p;
				}
			}
			topScorers.add(highestScorer);
			players.remove(highestScorer);
			
		}
		
		return topScorers;
	}
	
	public List<Player> mostMinutes(){
		List<Player> mosMinutes = new ArrayList<Player>();
		List<Player> players = playerRepository.findAll();
		Player highestMinutes = new Player();
		for(int i=0; i<=2; i++) {
			if(players.get(0).getAllMinutes()>=players.get(1).getAllMinutes()) {
				highestMinutes=players.get(0);
				
			}else {
				highestMinutes=players.get(1);
				
			}
			for(Player p:players) {
				if(p.getAllMinutes()>highestMinutes.getAllMinutes() && !p.equals(highestMinutes)) {
					highestMinutes=p;
				}
			}
			mosMinutes.add(highestMinutes);
			players.remove(highestMinutes);
			
		}
		
		return mosMinutes;
	}
	
	
}
