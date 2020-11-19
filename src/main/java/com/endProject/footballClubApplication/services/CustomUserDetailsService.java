package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.CustomUserDetails;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\users\\";
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\";
	@Autowired
	private UserRepository userRepository;
	
	// get user from repository depending on username and return it as myCustomUserDetails
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user =userRepository.findByUsername(username);
		CustomUserDetails userDetails = null;
		if (user != null) {
			// covert user details from repository to customUserDetails
			userDetails = new CustomUserDetails();
			userDetails.setUser(user);
			
		}else {
			throw new UsernameNotFoundException("User is is not found : "+ username); 
		}
		
		
		return userDetails;
	}
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	public User findByUserName(String username){
		return userRepository.findByUsername(username);
	}
	
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void save(User user,  MultipartFile file) throws IllegalStateException, IOException {
		 userRepository.save(user);
		 
		 if(!file.isEmpty()) {
			 File newDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 // transfer file to our directory and give it name of training id
			 file.transferTo(new File(PATH+user.getId()+".jpg"));
		 }
	}
	
	//delete training by id and delete file if such file exists
		public void deleteById(Integer id) {
				//check if file exists in our directory and delete it
				File file = new File(PATH+id+".jpg");
				 if(file.exists()) {
					 file.delete();
				 }
				 userRepository.deleteById(id);
			 
		}

}
