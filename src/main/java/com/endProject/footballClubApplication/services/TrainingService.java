package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Team;
import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.repositories.TeamRepository;
import com.endProject.footballClubApplication.repositories.TrainingRepository;



@Service 
public class TrainingService {
	
	@Autowired
	TrainingRepository trainingRepository;
	@Autowired
	TeamRepository teamRepository;
	
	public void save(Training training, MultipartFile file) throws IllegalStateException, IOException {
		 trainingRepository.save(training);
		 file.transferTo(new File("C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\trainings\\"+training.getId()+".jpg"));
	}
	
	public void deleteById(Integer id) {
		 trainingRepository.deleteById(id);
	}
	
	public List<Training> findAll(String keyword){
		if(keyword !=null) {
			return trainingRepository.findAll(keyword);
		}
		return trainingRepository.findAll();
	}
	
	public List<Training> findAll(){
		return trainingRepository.findAll();
	}
	
	public Optional<Training> finfById(Integer id){
		return trainingRepository.findById(id);
	}
}
