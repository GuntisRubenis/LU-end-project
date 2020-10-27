package com.endProject.footballClubApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.models.Training;
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
	
	
		@Query("SELECT t FROM Training t WHERE t.team.teamName LIKE %?1%")
		public List<Tournament> findAll(String Keyword);
		
	

}
