package com.endProject.footballClubApplication.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player extends Person{
	
	private String position;
	
	private String strongFoot;
	
	private String alternatePosition;
	
	// player can be part of one team only
	@ManyToOne
	@JoinColumn(name="teamId", insertable=false, updatable=false)
	private Team team;
	private Integer teamId;

	public Player(Integer id, String name, String surname, String age, String photo, String phone, String email,
			String position, String strongFoot, String alternatePosition) {
		super(id, name, surname, age, photo, phone, email);
		this.position = position;
		this.strongFoot = strongFoot;
		this.alternatePosition = alternatePosition;
	}
	
	public Player () {}
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStrongFoot() {
		return strongFoot;
	}

	public void setStrongFoot(String strongFoot) {
		this.strongFoot = strongFoot;
	}

	public String getAlternatePosition() {
		return alternatePosition;
	}

	public void setAlternatePosition(String alternatePosition) {
		this.alternatePosition = alternatePosition;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
	
	
	
	
}
