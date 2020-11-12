package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Post;

import com.endProject.footballClubApplication.repositories.PostRepository;



@Service 
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\posts\\";
	
	public void save(Post post, MultipartFile file) throws IllegalStateException, IOException {
		 postRepository.save(post);
		 // if file is not empty create new directory with teams name
		 if(!file.isEmpty()) {
			 File newDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 // transfer file to our directory and give it name of post id
			 file.transferTo(new File(PATH+post.getId()+".jpg")); 
		 }
	}
	
	//delete training by id and delete file if such file exists
	public void deleteById(Integer id) {
		//check if file exists in our directory and delete it
		File file = new File(PATH+id+".jpg");
		if(file.exists()) {
				file.delete();
			}
		postRepository.deleteById(id);
			 
	}
	
	public List<Post> findAll(){
		return postRepository.findAll();
	}
	
	public List<Post> findAll(String keyword){
		if (keyword != null) {
				return postRepository.findAll(keyword);
		}
		return postRepository.findAll();
	}
	
	public Optional<Post> finfById(Integer id){
		return postRepository.findById(id);
	}	
}
