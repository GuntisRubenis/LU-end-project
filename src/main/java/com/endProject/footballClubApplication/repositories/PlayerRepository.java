package com.endProject.footballClubApplication.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.endProject.footballClubApplication.models.Player;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
	
	//Select coaches based on criteria, name, surname, category and overload findAll function
		// Coach should be a model name not database table name
		@Query("SELECT p FROM Player p WHERE p.name LIKE %?1%"
				+"OR p.surname LIKE %?1%"
				+"OR p.position LIKE %?1%"
				+"OR p.team.teamName LIKE %?1%")
		public Page<Player> findAll(String Keyword, Pageable pageable);
}
