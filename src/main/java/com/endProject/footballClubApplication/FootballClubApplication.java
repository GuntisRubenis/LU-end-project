package com.endProject.footballClubApplication;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.endProject.footballClubApplication.controllers.CoachController;

@SpringBootApplication
public class FootballClubApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(FootballClubApplication.class, args);
	}
	
}
