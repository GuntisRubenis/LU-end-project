package com.endProject.footballClubApplication.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Player extends Person{
	
	private String position;
	
	private String strongFoot;
	
	private String alternatePosition;
	
	// player can be part of one team only
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="teamId", insertable=false, updatable=false)
	private Team team;
	private Integer teamId;
	
	@ManyToMany(mappedBy = "players")
    private List<Training> trainings = new ArrayList<Training>();
	
	@ManyToMany(mappedBy = "players")
    private List<Tournament> tournaments = new ArrayList<Tournament>();
	
	@OneToMany(mappedBy="player", cascade = CascadeType.REMOVE)
	private List<Statistics> statictics;

	public Player(Integer id, String name, String surname, String age, String photo, String phone, String email,
			String position, String strongFoot, String alternatePosition) {
		super(id, name, surname, age, photo, phone, email);
		this.position = position;
		this.strongFoot = strongFoot;
		this.alternatePosition = alternatePosition;
	}
	
	public Player () {
		
	}
	

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

	public List<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Training> trainings) {
		this.trainings = trainings;
	}
	
	public Integer countTrainings () {
		return trainings.size();
	}

	public List<Tournament> getTournaments() {
		return tournaments;
	}

	public void setTournaments(List<Tournament> tournaments) {
		this.tournaments = tournaments;
	}

	public List<Statistics> getStatictics() {
		return statictics;
	}

	public void setStatictics(List<Statistics> statictics) {
		this.statictics = statictics;
	}
	
	public Integer getGoals (Integer tournamentId) {
		Integer goals = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			if(stat.getTournamentId()==tournamentId) {
				goals = stat.getGoals();
			}
		}
		return goals;
	}
	
	public Integer getAssists(Integer tournamentId) {
		Integer assists = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			if(stat.getTournamentId()==tournamentId) {
				assists = stat.getAssists();
			}
		}
		return assists;
	}
	
	public Integer getMinutes(Integer tournamentId) {
		Integer minutes = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			if(stat.getTournamentId()==tournamentId) {
				minutes = stat.getMinutes();
			}
		}
		return minutes;
	}
	
	public Integer getAllGoals () {
		Integer golas = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			golas+=stat.getGoals();
		}
		return golas;
	}
	
	public Integer getAllAssists () {
		Integer assists = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			assists+=stat.getAssists();
		}
		return assists;
	}
	
	public Integer getAllMinutes() {
		Integer minutes = 0;
		for (Integer i=0; i<statictics.size(); i++) {
			Statistics stat = statictics.get(i);
			minutes+=stat.getMinutes();
		}
		return minutes;
	}
	
	public Boolean imageExists() {
		String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\players\\"+super.getId()+".jpg";
		File file = new File(PATH);
		if(file.exists()) {
			return true;
		}
		return false;
	}
	
}
