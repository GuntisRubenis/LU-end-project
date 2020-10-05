package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.repositories.PlayerRepository;



@Service 
public class PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	public void save(Player player) {
		 playerRepository.save(player);
	}
	
	public void deleteById(Integer id) {
		 playerRepository.deleteById(id);
	}
	
	// check if keyword is typed and then call findAll function based on keyword
	public List<Player> findAll(String keyword){
		if (keyword != null) {
				return playerRepository.findAll(keyword);
		}
		return playerRepository.findAll();
	}
	
	public Optional<Player> finfById(Integer id){
		return playerRepository.findById(id);
	}
	
	//upload new file to end direction and rename it as user id, so it have unique name 
	public void uploadFile(Integer id, MultipartFile file) throws IllegalStateException, IOException {
			
		file.transferTo(new File("C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\players\\"+id+".jpg"));
			
	}
}
