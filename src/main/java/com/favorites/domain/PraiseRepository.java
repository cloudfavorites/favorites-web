package com.favorites.domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PraiseRepository extends JpaRepository<Praise, Long> {


	Long countByCollectId(Long collectId);
	
	Praise findByPraiseIdAndCollectId(Long userId,Long collectId); 
}