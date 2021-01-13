package com.endProject.footballClubApplication.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Training{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String description;
	
	@DateTimeFormat( pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="teamId", insertable=false, updatable=false)
	private Team team;
	private Integer teamId;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
        name = "player_training", 
        joinColumns = { @JoinColumn(name = "trainingId") }, 
        inverseJoinColumns = { @JoinColumn(name = "playerId") }
    )
    private List<Player> players = new ArrayList<Player>();
	
	

	public Training() {
		
	}

	

	public Training(Integer id, String description, Date date, Team team, Integer teamId, List<Player> players) {
		this.id = id;
		this.description = description;
		this.date = date;
		this.team = team;
		this.teamId = teamId;
		this.players = players;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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



	public List<Player> getPlayers() {
		return players;
	}



	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public Boolean fileExsists() {
		String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\trainings\\"+this.team.getTeamName()+"\\"+this.id+".pdf";
		File file = new File(PATH);
		if(file.exists()) {
			return true;
		}
		return false;
	}

	
	
	
}
