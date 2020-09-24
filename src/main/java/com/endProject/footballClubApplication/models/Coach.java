package com.endProject.footballClubApplication.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;



@Entity
public class Coach extends Person{
	
	private String category;
	
	@OneToMany(mappedBy="coach")
	private List<Team> teams;
	
	@OneToMany(mappedBy="asistantCoach")
	private List<Team> asistanTeams;

	public Coach(Integer id, String name, String surname, String age, String photo, String phone, String email,
			String category) {
		super(id, name, surname, age, photo, phone, email);
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Team> getAsistanTeams() {
		return asistanTeams;
	}

	public void setAsistanTeams(List<Team> asistanTeams) {
		this.asistanTeams = asistanTeams;
	}
	
	
	
	
}
