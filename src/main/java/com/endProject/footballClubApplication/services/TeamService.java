package com.endProject.footballClubApplication.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.repositories.TeamRepository;



@Service 
public class TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	
	public void save(Team team) {
		 teamRepository.save(team);
	}
	
	public void delete(Team team) {
		 teamRepository.save(team);
	}
	
	public List<Team> findAll(){
		return teamRepository.findAll();
	}
	
	public Optional<Team> finfById(Integer id){
		return teamRepository.findById(id);
	}
}
