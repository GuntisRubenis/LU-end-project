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
	
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\teams\\";
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\teams\\";
	@Autowired
	TeamRepository teamRepository;
	
	public void save(Team team, MultipartFile file) throws IllegalStateException, IOException {
		 teamRepository.save(team);
		 if(!file.isEmpty()) {
			 File newDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 file.transferTo(new File(PATH+team.getId()+".jpg"));
		 }
		 
	}
	
	public void deleteById(Integer id) {
		//check if file exists in our directory and delete it
		File file = new File(PATH+id+".jpg");
			if(file.exists()) {
			file.delete();
				}
		 teamRepository.deleteById(id);
	}
	
	public List<Team> findAll(String keyword){
		if(keyword !=null) {
			return teamRepository.findAll(keyword);
		}
		return teamRepository.findAll();
	}
	
	public List<Team> findAll(){
		return teamRepository.findAll();
	}
	
	public Optional<Team> finfById(Integer id){
		return teamRepository.findById(id);
	}
}
