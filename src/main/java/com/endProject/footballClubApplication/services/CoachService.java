package com.endProject.footballClubApplication.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.repositories.CoachRepository;



@Service 
public class CoachService {
	
	@Autowired
	CoachRepository coachRepository;
	
	public void save(Coach coach) {
		 coachRepository.save(coach);
	}
	
	public void delete(Coach coach) {
		 coachRepository.save(coach);
	}
	
	public List<Coach> findAll(){
		return coachRepository.findAll();
	}
	
	public Optional<Coach> finfById(Integer id){
		return coachRepository.findById(id);
	}
}
