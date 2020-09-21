package com.endProject.footballClubApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endProject.footballClubApplication.models.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
