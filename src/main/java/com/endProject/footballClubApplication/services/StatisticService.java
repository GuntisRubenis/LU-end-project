package com.endProject.footballClubApplication.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.endProject.footballClubApplication.models.Statistics;
import com.endProject.footballClubApplication.repositories.StatisticRepository;



@Service 
public class StatisticService {
	
	@Autowired
	StatisticRepository statisticRepository;
	
	public void save(Statistics statistics) {
		 statisticRepository.save(statistics);
	}
	
	public void deleteByid(Integer id) {
		 statisticRepository.deleteById(id);
	}
	
	public void delete(Statistics statistics) {
		 statisticRepository.delete(statistics);
	}
	
	public List<Statistics> findAll(){
		return statisticRepository.findAll();
	}
	
	public Optional<Statistics> finfById(Integer id){
		return statisticRepository.findById(id);
	}	
}
