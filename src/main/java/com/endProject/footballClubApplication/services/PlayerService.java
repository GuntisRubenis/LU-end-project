package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.repositories.PlayerRepository;



@Service 
public class PlayerService {
	
	@Autowired
	PlayerRepository playerRepository;
	
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\players\\";
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\players\\";
	public void save(Player player,  MultipartFile file) throws IllegalStateException, IOException {
		 playerRepository.save(player);
		 
		 if(!file.isEmpty()) {
			 File newDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 file.transferTo(new File(PATH+player.getId()+".jpg"));
		 }
	}
	
	public void save(Player player){
		
		playerRepository.save(player);
	}
	
	public void deleteById(Integer id) {
		//check if file exists in our directory and delete it
		File file = new File(PATH+id+".jpg");
		 if(file.exists()) {
			 file.delete();
		 }
		 playerRepository.deleteById(id);
	}
	
	public Page<Player> listAll(int pageNum, String keyword) {
	    int pageSize = 5;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    if(keyword != null) {
	    	return playerRepository.findAll(keyword, pageable);
	    } 
	    return playerRepository.findAll(pageable);
	}
	
	public Optional<Player> finfById(Integer id){
		return playerRepository.findById(id);
	}
	
	public List<Player> findAll(){
		return playerRepository.findAll();
	}
	
}
