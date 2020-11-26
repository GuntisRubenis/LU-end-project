package com.endProject.footballClubApplication.repositories;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Tournament;
import com.endProject.footballClubApplication.models.Training;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
	
	@Query("SELECT c FROM Tournament c WHERE c.team.teamName LIKE %?1%"
			+"OR c.name LIKE %?1%")
	public Page<Tournament> findAll(String Keyword, Pageable pageable);
	
	@Query("SELECT t FROM Tournament t WHERE t.date >= :startDate AND t.date <= :endDate")
	public Page<Tournament> findAll(Pageable pageable, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	@Query("SELECT t FROM Tournament t WHERE t.team.teamName LIKE %:keyword% "+
	"OR t.name LIKE %:keyword% AND t.date >= :startDate AND t.date <= :endDate")
	public Page<Tournament> findAll(@Param("keyword")String keyword,Pageable pageable, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
