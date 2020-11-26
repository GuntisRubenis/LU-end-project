package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.models.Player;
import com.endProject.footballClubApplication.models.Post;
import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.repositories.PostRepository;



@Service 
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\posts\\";
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\posts\\";
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
	
	//delete post by id and delete file if such file exists
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
	
	public Page<Post> listAll(int pageNum, String keyword) {
	    int pageSize = 6;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    if(keyword != null) {
	    	return postRepository.findAll(keyword, pageable);
	    } 
	    return postRepository.findAll(pageable);
	}
	
	public Optional<Post> finfById(Integer id){
		return postRepository.findById(id);
	}
	
	public Page<Post> listAll(int pageNum) {
	    int pageSize = 6;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    return postRepository.findAll(pageable);
	}
	
	public Page<Post> listAllDate(int pageNum, String keyword, String startDate, String endDate) throws ParseException {
	    int pageSize = 10;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    //check if keyword and dates are entered
	    if(keyword != null && !keyword.equals("") && startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			//parse string to date
	    	Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates+keyword");
	    	return postRepository.findAll(keyword, pageable, date1, date2);
	    }
	    //check if only dates are entered
	    if(startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates");
	    	return postRepository.findAll(pageable, date1, date2);
	    }
	    // check if only keyword is entered
	    if(keyword != null && !keyword.equals("")) {
	    	System.out.println("Hello keyword-> "+ keyword);
	    	return postRepository.findAll(keyword, pageable);
	    }
	    System.out.println("Hello all");
	    return postRepository.findAll(pageable);
	}
}
