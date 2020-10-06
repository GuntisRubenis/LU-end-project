package com.endProject.footballClubApplication.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Coach extends Person{
	
	private String category;
	
	// one coach can coach more than one team
	@OneToMany(mappedBy="coach")
	private List<Team> teams;
	
	// one coach can be  assistant coach to more than one team
	@OneToMany(mappedBy="assistantCoach")
	private List<Team> assistantTeams;

	public Coach(Integer id, String name, String surname, String age, String photo, String phone, String email,
			String category) {
		super(id, name, surname, age, photo, phone, email);
		this.category = category;
	}
	
	public Coach() {
		
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

	public List<Team> getAssistantTeams() {
		return assistantTeams;
	}

	public void setAssistantTeams(List<Team> assistantTeams) {
		this.assistantTeams = assistantTeams;
	}
	
	
}
