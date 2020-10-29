package com.endProject.footballClubApplication.models;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;



@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tournament{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String address;
	
	@DateTimeFormat( pattern = "yyyy-MM-dd")
	private Date date;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name="teamId", insertable=false, updatable=false)
	private Team team;
	private Integer teamId;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
        name = "player_tournament", 
        joinColumns = { @JoinColumn(name = "tournamentId") }, 
        inverseJoinColumns = { @JoinColumn(name = "playerId") }
    )
    private List<Player> players = new ArrayList<Player>();
	
	
	@OneToMany(mappedBy="tournament", cascade = CascadeType.REMOVE)
	private List<Statistics> statictics;

	public Tournament() {
		
	}

	public Tournament(Integer id, String name, String address, Date date, Team team, Integer teamId) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.date = date;
		this.team = team;
		this.teamId = teamId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<Statistics> getStatictics() {
		return statictics;
	}

	public void setStatictics(List<Statistics> statictics) {
		this.statictics = statictics;
	}
	
}
