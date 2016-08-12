package com.favorites.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {

	Config findByUserId(Long userId);
	
}