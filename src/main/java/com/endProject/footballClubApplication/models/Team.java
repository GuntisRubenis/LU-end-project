package com.endProject.footballClubApplication.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String teamName;
	
	@ManyToOne
	@JoinColumn(name="coachId", insertable=false, updatable=false)
	private Coach coach;
	private Integer coachId;
	
	@ManyToOne
	@JoinColumn(name="asistantCoachId", insertable=false, updatable=false)
	private Coach asistantCoach;
	private Integer asistantCoachId;
	
	@OneToMany(mappedBy="team")
	private List<Player> players;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCoachId() {
		return coachId;
	}

	public void setCoachId(Integer coachId) {
		this.coachId = coachId;
	}

	public Coach getAsistantCoach() {
		return asistantCoach;
	}

	public void setAsistantCoach(Coach asistantCoach) {
		this.asistantCoach = asistantCoach;
	}

	public Integer getAsistantCoachId() {
		return asistantCoachId;
	}

	public void setAsistantCoachId(Integer asistantCoachId) {
		this.asistantCoachId = asistantCoachId;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	

}
