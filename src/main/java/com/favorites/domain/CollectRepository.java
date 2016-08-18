package com.favorites.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CollectRepository extends JpaRepository<Collect, Long> {

	Page<Collect> findAll(Pageable pageable);
	
	Page<Collect> findByUserId(Long userId,Pageable pageable);
	
	Long countByUserId(Long userId);
	 
	Page<Collect> findByFavoritesId(Long favoritesId,Pageable pageable);
	
	List<Collect> findByFavoritesIdAndUrlAndUserId(Long favoritesId,String url,Long userId);
	
	@Modifying
	@Query("update Collect c set c.type = ?1 where c.id = ?2")
	int modifyById(String type, long id);
	
	@Transactional
	@Modifying
	@Query("delete from Collect where favoritesId = ?1")
	void deleteByFavoritesId(Long favoritesId);


}