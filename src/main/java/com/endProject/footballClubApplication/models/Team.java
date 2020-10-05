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
	
	// team can have one coach
	@ManyToOne
	@JoinColumn(name="coachId", insertable=false, updatable=false)
	private Coach coach;
	private Integer coachId;
	
	// team can have one assistant coach
	@ManyToOne
	@JoinColumn(name="assistantCoachId", insertable=false, updatable=false)
	private Coach assistantCoach;
	private Integer assistantCoachId;
	
	// every team have list of players
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


	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Coach getAssistantCoach() {
		return assistantCoach;
	}

	public void setAssistantCoach(Coach assistantCoach) {
		this.assistantCoach = assistantCoach;
	}

	public Integer getAssistantCoachId() {
		return assistantCoachId;
	}

	public void setAssistantCoachId(Integer assistantCoachId) {
		this.assistantCoachId = assistantCoachId;
	}

}
