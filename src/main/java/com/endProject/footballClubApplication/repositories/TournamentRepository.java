package com.endProject.footballClubApplication.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
	
	@Query("SELECT c FROM Tournament c WHERE c.team.teamName LIKE %?1%")
	public Page<Tournament> findAll(String Keyword, Pageable pageable);
}
