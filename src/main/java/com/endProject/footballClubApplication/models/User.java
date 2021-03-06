package com.endProject.footballClubApplication.models;


import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends Person{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min = 4, max =25 )
	private String username;
	
	private String password;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
				name = "user_role", 
				joinColumns = @JoinColumn(name="user_id"), 
				inverseJoinColumns = @JoinColumn(name="role_id")
				)
	private List<Role> roles = new ArrayList<Role>();

	public User () {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public Boolean imageExists() {
		String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\users\\"+this.id+".jpg";
		File file = new File(PATH);
		if(file.exists()) {
			return true;
		}
		return false;
	}

}
