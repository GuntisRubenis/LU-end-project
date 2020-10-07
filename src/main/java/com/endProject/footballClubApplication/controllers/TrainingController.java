package com.endProject.footballClubApplication.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



import com.endProject.footballClubApplication.models.Training;
import com.endProject.footballClubApplication.services.TeamService;
import com.endProject.footballClubApplication.services.TrainingService;



@Controller
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private TeamService teamService;
	
	
	@RequestMapping("/rest/training")
	public String trainingPage (Model model, @Param("keyword") String keyword) {
		List<Training> trainings = trainingService.findAll(keyword);
		model.addAttribute("trainings", trainings);
		model.addAttribute("teams", teamService.findAll());
		return "training";
	}
	
	@PostMapping("/rest/training/addNew")
	public String addTraining(Training training, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		trainingService.save(training, file);
		return "redirect:";
	}
	
	@RequestMapping(value="/rest/training/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteTraining(Integer id) {
		trainingService.deleteById(id);
		return "redirect:/rest/training";
	}
	
	@RequestMapping("/rest/training/findById") 
	@ResponseBody
	public Optional<Training> findById(Integer id)
	{
		return trainingService.finfById(id);
	}
	
	
	@RequestMapping(value="/rest/training/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Training training, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		trainingService.save(training, file);
		return "redirect:/rest/training";
	}
	
}
