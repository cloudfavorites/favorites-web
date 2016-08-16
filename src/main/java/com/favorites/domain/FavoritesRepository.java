package com.favorites.domain;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

	List<Favorites> findByUserId(Long userId);
	
	Favorites findByUserIdAndName(Long userId,String name);
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update Favorites f set f.count=(f.count+1),f.lastModifyTime =:lastModifyTime where f.id =:id")
	void updateCountById(@Param("id") Long id,@Param("lastModifyTime") Long lastModifyTime);


}