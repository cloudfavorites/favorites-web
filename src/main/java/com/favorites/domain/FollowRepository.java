package com.favorites.domain;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	@Query("select u.userName from Follow f ,User u  where f.userId=:userId and f.followId = u.id and f.status = 'follow'")
	List<String> findByUserId(@Param("userId") Long userId);

}