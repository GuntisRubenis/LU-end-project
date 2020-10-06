package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.repositories.TeamRepository;



@Service 
public class TeamService {
	
	@Autowired
	TeamRepository teamRepository;
	
	public void save(Team team, MultipartFile file) throws IllegalStateException, IOException {
		 teamRepository.save(team);
		 file.transferTo(new File("C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\teams\\"+team.getId()+".jpg"));
	}
	
	public void deleteById(Integer id) {
		 teamRepository.deleteById(id);
	}
	
	public List<Team> findAll(){
		return teamRepository.findAll();
	}
	
	public Optional<Team> finfById(Integer id){
		return teamRepository.findById(id);
	}
}
