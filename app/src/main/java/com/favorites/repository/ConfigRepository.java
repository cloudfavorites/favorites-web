package com.favorites.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.favorites.domain.Config;

public interface ConfigRepository extends JpaRepository<Config, Long> {

	Config findByUserId(Long userId);

	Config findById(long id);

	Config findByUserIdAndDefaultFavorties(Long userId,String defaultFavorites);
	
	@Transactional
	@Modifying
	@Query("update Config set defaultCollectType=?2,lastModifyTime =?3 where id = ?1")
	int updateCollectTypeById(Long id,String value,Long lastModifyTime);
	
	@Transactional
	@Modifying
	@Query("update Config set defaultModel=?2,lastModifyTime =?3 where id = ?1")
	int updateModelTypeById(Long id,String value,Long lastModifyTime);
	
	@Transactional
	@Modifying
	@Query("update Config set defaultFavorties=?2,lastModifyTime =?3 where id = ?1")
	int updateFavoritesById(Long id,String value,Long lastModifyTime);
	
}