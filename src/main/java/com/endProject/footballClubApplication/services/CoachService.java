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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.repositories.CoachRepository;



@Service 
public class CoachService {
	
	@Autowired
	CoachRepository coachRepository;
	
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\coaches\\";
	
	public void save(Coach coach, MultipartFile file) throws IllegalStateException, IOException {
		 coachRepository.save(coach);
		 if(!file.isEmpty()) {
			 file.transferTo(new File(PATH+coach.getId()+".jpg"));
		 }
	}
	
	public void deleteByid(Integer id) {
		//check if file exists in our directory and delete it
				File file = new File(PATH+id+".jpg");
				 if(file.exists()) {
					 file.delete();
				 }
		 coachRepository.deleteById(id);
	}
	
	public List<Coach> findAll(){
		return coachRepository.findAll();
	}
	
	public Optional<Coach> finfById(Integer id){
		return coachRepository.findById(id);
	}
	
	public Page<Coach> listAll(int pageNum, String keyword) {
	    int pageSize = 6;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    if(keyword != null) {
	    	return coachRepository.findAll(keyword, pageable);
	    } 
	    return coachRepository.findAll(pageable);
	}
}
