package com.endProject.footballClubApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.endProject.footballClubApplication.models.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
	
	//Select coaches based on criteria, name, surname, category and overload findAll function
	// Coach should be a model name not database table name
	@Query("SELECT t FROM Team t WHERE t.teamName LIKE %?1%")
	public List<Team> findAll(String Keyword);

}
