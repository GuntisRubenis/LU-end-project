package com.endProject.footballClubApplication.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.endProject.footballClubApplication.models.Role;
import com.endProject.footballClubApplication.repositories.RoleRepository;



@Service 
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public void save(Role role) {
		 roleRepository.save(role);
	}
	
	public void deleteByid(Integer id) {
		 roleRepository.deleteById(id);
	}

	
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	
	public Optional<Role> finfById(Integer id){
		return roleRepository.findById(id);
	}	
}
