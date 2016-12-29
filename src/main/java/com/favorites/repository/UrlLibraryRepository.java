package com.favorites.repository;

import com.favorites.domain.UrlLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlLibraryRepository extends JpaRepository<UrlLibrary, Long> {

}