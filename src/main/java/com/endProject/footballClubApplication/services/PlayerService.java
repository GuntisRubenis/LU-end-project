package com.endProject.footballClubApplication.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.repositories.PlayerRepository;



@Service 
public class PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	public void save(Player player) {
		 playerRepository.save(player);
	}
	
	public void delete(Player player) {
		 playerRepository.save(player);
	}
	
	public List<Player> findAll(){
		return playerRepository.findAll();
	}
	
	public Optional<Player> finfById(Integer id){
		return playerRepository.findById(id);
	}
}
