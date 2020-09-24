package com.endProject.footballClubApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.endProject.footballClubApplication.models.Coach;

public interface CoachRepository extends JpaRepository<Coach, Integer> {

}
