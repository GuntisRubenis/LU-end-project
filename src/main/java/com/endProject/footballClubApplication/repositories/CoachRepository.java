package com.endProject.footballClubApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.endProject.footballClubApplication.models.Coach;


public interface CoachRepository extends JpaRepository<Coach, Integer> {
	//Select coaches based on criteria, name, surname, category and overload findAll function
	// Coach should be a model name not database table name
	@Query("SELECT c FROM Coach c WHERE c.name LIKE %?1%"
			+"OR c.surname LIKE %?1%"
			+"OR c.category LIKE %?1%")
	public List<Coach> findAll(String Keyword);

}
