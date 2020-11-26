package com.endProject.footballClubApplication.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.endProject.footballClubApplication.models.Post;
import com.endProject.footballClubApplication.models.Training;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	
	@Query("SELECT t FROM Post t WHERE t.title LIKE %?1%")
	public Page<Post> findAll(String Keyword,Pageable pageable);
	
	@Query("SELECT t FROM Post t WHERE t.date >= :startDate AND t.date <= :endDate")
	public Page<Post> findAll(Pageable pageable, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	@Query("SELECT t FROM Post t WHERE t.title LIKE %:keyword% AND t.date >= :startDate AND t.date <= :endDate")
	public Page<Post> findAll(@Param("keyword")String keyword,Pageable pageable, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
	

}
