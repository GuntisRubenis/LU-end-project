package com.endProject.footballClubApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.endProject.footballClubApplication.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("SELECT p FROM Post p WHERE p.title LIKE %?1%")
	public List<Post> findAll(String Keyword);

}
