package com.endProject.footballClubApplication.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// this class maps user details dynamically from user repository

public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	@OneToMany(mappedBy="user")
	private List<Post> posts;
	
	//Get all user roles and return them as a list of simple granted authorities 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return user.getRoles().stream().
				map(role-> new SimpleGrantedAuthority("ROLE_"+role.getRole())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	

}
