package com.endProject.footballClubApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Player;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

}
