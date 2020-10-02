package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.repositories.CoachRepository;



@Service 
public class CoachService {
	
	
	
	@Autowired
	CoachRepository coachRepository;
	
	public void save(Coach coach) {
		 coachRepository.save(coach);
	}
	
	public void deleteByid(Integer id) {
		 coachRepository.deleteById(id);
	}
	
	public List<Coach> findAll(){
		return coachRepository.findAll();
	}
	
	public Optional<Coach> finfById(Integer id){
		return coachRepository.findById(id);
	}
	//upload new file to end direction and rename it as user id, so it have unique name 
	public void uploadFile(Integer id, MultipartFile file) throws IllegalStateException, IOException {
		
		file.transferTo(new File("C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\coaches\\"+id+".jpg"));
		
	}
	
	
	
}
