package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.endProject.footballClubApplication.models.Training;

import com.endProject.footballClubApplication.repositories.TrainingRepository;



@Service 
public class TrainingService {
	
	@Autowired
	TrainingRepository trainingRepository;
	
	//String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\footballClubApplication\\src\\main\\resources\\static\\img\\trainings\\";
	String PATH = "C:\\Users\\taken305\\Downloads\\JAVA_SPRING_BOOT\\footballClubApplication\\uploads\\trainings\\";
	public void save(Training training, MultipartFile file, String teamName) throws IllegalStateException, IOException {
		 trainingRepository.save(training);
		 // if file is not empty create new directory with teams name
		 if(!file.isEmpty()) {
			 File trainingDirectory = new File(PATH);
			 // if directory don`t exist make new directory
			 if(!trainingDirectory.exists()) {
				 trainingDirectory.mkdir();
			 }
			 File newDirectory = new File(PATH+teamName+"\\");
			 if(!newDirectory.exists()) {
				 newDirectory.mkdir();
			 }
			 // transfer file to our directory and give it name of training id
			 file.transferTo(new File(PATH+teamName+"\\"+training.getId()+".pdf")); 
		 }
	}
	
	//delete training by id and delete file if such file exists
	public void deleteById(Integer id, String teamName) {
			//check if file exists in our directory and delete it
			File file = new File(PATH+teamName+"\\"+id+".jpg");
			 if(file.exists()) {
				 file.delete();
			 }
			 trainingRepository.deleteById(id);
		 
	}
	
	public void deleteById(Integer id) {
		 trainingRepository.deleteById(id);
	}
	
	public Page<Training> listAllDate(int pageNum, String keyword, String startDate, String endDate) throws ParseException {
	    int pageSize = 10;
	    Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
	    //check if keyword and dates are entered
	    if(keyword != null && !keyword.equals("") && startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			//parse string to date
	    	Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates+keyword");
	    	return trainingRepository.findAll(keyword, pageable, date1, date2);
	    }
	    //check if only dates are entered
	    if(startDate != null && endDate != null && !startDate.equals("") && !endDate.equals("")) {
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Date date1 = formatter.parse(startDate);
			Date date2 = formatter.parse(endDate);
			System.out.println("Hello dates");
	    	return trainingRepository.findAll(pageable, date1, date2);
	    }
	    // check if only keyword is entered
	    if(keyword != null && !keyword.equals("")) {
	    	System.out.println("Hello keyword-> "+ keyword);
	    	return trainingRepository.findAll(keyword, pageable);
	    }
	    System.out.println("Hello all");
	    return trainingRepository.findAll(pageable);
	}
	
	public List<Training> findAll(){
		return trainingRepository.findAll();
	}
	
	public Optional<Training> finfById(Integer id){
		return trainingRepository.findById(id);
	}
	
	public void save(Training training) {
		trainingRepository.save(training);
	}
	
}
