package com.favorites.domain;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites, Long> {

	List<Favorites> findByUserId(Long userId);
	
	Favorites findByUserIdAndName(Long userId,String name);


}