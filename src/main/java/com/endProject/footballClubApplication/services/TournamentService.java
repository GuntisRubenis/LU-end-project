package com.endProject.footballClubApplication.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.repositories.TournamentRepository;



@Service 
public class TournamentService {
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	public void save(Tournament tournament) {
		tournamentRepository.save(tournament);
	}
	
	public void deleteById(Integer id) {
		tournamentRepository.deleteById(id);
	}
	
	public Page<Tournament> listAll(int pageNum, String keyword) {
	    int pageSize = 2;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    if(keyword != null) {
	    	return tournamentRepository.findAll(keyword, pageable);
	    } 
	    return tournamentRepository.findAll(pageable);
	}
	
	public List<Tournament> findAll(){
		return tournamentRepository.findAll();
	}
	
	public Optional<Tournament> finfById(Integer id){
		return tournamentRepository.findById(id);
	}
}
