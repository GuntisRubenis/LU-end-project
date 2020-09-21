package com.endProject.footballClubApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.endProject.footballClubApplication.models.CustomUserDetails;
import com.endProject.footballClubApplication.models.User;
import com.endProject.footballClubApplication.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
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

}
