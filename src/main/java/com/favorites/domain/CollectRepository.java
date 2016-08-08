package com.favorites.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectRepository extends JpaRepository<Collect, Long> {

	Page<Collect> findAll(Pageable pageable);
	
	Page<Collect> findByUserId(Long userId,Pageable pageable);


}