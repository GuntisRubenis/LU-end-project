package com.endProject.footballClubApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endProject.footballClubApplication.models.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

}
