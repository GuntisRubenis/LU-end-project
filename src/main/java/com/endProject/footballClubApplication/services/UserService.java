package com.endProject.footballClubApplication.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.UserRepository;
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public void save(User user) {
		 userRepository.save(user);
	}
	
	public void delete(User user) {
		 userRepository.save(user);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	

}
