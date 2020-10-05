package com.endProject.footballClubApplication.repositories;

import java.util.List;

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
				+"OR p.strongFoot LIKE %?1%")
		public List<Player> findAll(String Keyword);

}
