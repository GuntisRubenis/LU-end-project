package com.endProject.footballClubApplication.services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.models.Training;
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
	
	public List<Tournament> findAll(){
		return tournamentRepository.findAll();
	}
	
	public Optional<Tournament> finfById(Integer id){
		return tournamentRepository.findById(id);
	}
	
	public Page<Tournament> listAllDate(int pageNum, String keyword, String startDate, String endDate) throws ParseException {
	    int pageSize = 10;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    //check if keyword and dates are entered
	    if(keyword != null && !keyword.equals("") && startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			//parse string to date
	    	Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates+keyword");
	    	return tournamentRepository.findAll(keyword, pageable, date1, date2);
	    }
	    //check if only dates are entered
	    if(startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates");
	    	return tournamentRepository.findAll(pageable, date1, date2);
	    }
	    // check if only keyword is entered
	    if(keyword != null && !keyword.equals("")) {
	    	System.out.println("Hello keyword-> "+ keyword);
	    	return tournamentRepository.findAll(keyword, pageable);
	    }
	    System.out.println("Hello all");
	    return tournamentRepository.findAll(pageable);
	}
}
