package com.endProject.footballClubApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Statistics;
import com.endProject.footballClubApplication.models.Training;
@Repository
public interface StatisticRepository extends JpaRepository<Statistics, Integer> {
	
}
