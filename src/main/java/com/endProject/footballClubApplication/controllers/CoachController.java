package com.endProject.footballClubApplication.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.print.attribute.standard.PageRanges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.endProject.footballClubApplication.models.Coach;
import com.endProject.footballClubApplication.services.CoachService;



@Controller
public class CoachController {
	
	@Autowired
	private CoachService coachService;
	
	@RequestMapping("/rest/coach/{pageNum}")
	public String viewPage(Model model,@PathVariable(name = "pageNum") int pageNum, @Param("keyword") String keyword) {
	     
	    Page<Coach> page = coachService.listAll(pageNum,keyword);
	     
	    List<Coach> listProducts = page.getContent();
	    if (page.getTotalPages() != 0) {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("coaches", listProducts);
	    }else {
	    	model.addAttribute("currentPage", pageNum);
		    model.addAttribute("totalPages", 1);
		    model.addAttribute("totalItems", page.getTotalElements());
		    model.addAttribute("coaches", listProducts);
	    }
	    
	     
	    return "coach";
	}
	
	@PostMapping("/rest/coach/addNew")
	public String addCoach(Coach coach, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		coachService.save(coach,file);
		redirectAttribute.addFlashAttribute("successMessage", "Coach added succesfully!!!");
		return "redirect:/rest/coach/1";
	}
	
	@RequestMapping(value="/rest/coach/delete", method = {RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteCoach(Integer id,RedirectAttributes redirectAttribute) {
		coachService.deleteByid(id);
		redirectAttribute.addFlashAttribute("deleteMessage", "Coach deleted succesfully!!!");
		return "redirect:/rest/coach/1";
	}
	
	@RequestMapping("/rest/coach/findById") 
	@ResponseBody
	public Optional<Coach> findById(Integer id)
	{
		return coachService.finfById(id);
	}
	
	@RequestMapping(value="/rest/coach/update", method = {RequestMethod.POST, RequestMethod.GET})
	public String update(Coach coach, @RequestParam("file") MultipartFile file,RedirectAttributes redirectAttribute) throws IllegalStateException, IOException {
		coachService.save(coach,file);
		redirectAttribute.addFlashAttribute("successMessage", "Coach edited succesfully!!!");
		return "redirect:/rest/coach/1";
	}
	
	
	@RequestMapping(value="/rest/coach/coachDetails")
	public String coachDetails(Model model, Integer id ) {
		Optional<Coach> coach = coachService.finfById(id);
		if(coach.isPresent()) {
			model.addAttribute("coach", coach.get());
			model.addAttribute("teams", coach.get().getTeams());
			model.addAttribute("assistantTeams", coach.get().getAssistantTeams());
		}
		return "coachDetails"; 
	}
	
}
