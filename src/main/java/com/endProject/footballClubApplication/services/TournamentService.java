package com.endProject.footballClubApplication.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	
	public List<Tournament> findAll(String keyword){
		if(keyword !=null) {
			return tournamentRepository.findAll(keyword);
		}
		return tournamentRepository.findAll();
	}
	
	public List<Tournament> findAll(){
		return tournamentRepository.findAll();
	}
	
	public Optional<Tournament> finfById(Integer id){
		return tournamentRepository.findById(id);
	}
}
