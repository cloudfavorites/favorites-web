package com.favorites.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	@Query("select u.userName from Follow f ,User u  where f.userId=:userId and f.followId = u.id and f.status = 'follow'")
	List<String> findByUserId(@Param("userId") Long userId);
	
	Integer countByUserIdAndStatus(Long userId,String status);
	
	Integer countByFollowIdAndStatus(String followId,String status);
	
	@Query("select u.userName , u.introduction  ,u.profilePicture ,u.id  from Follow f ,User u where f.userId=:userId and f.followId = u.id and f.status = 'follow'")
	List<String> findFollowUserByUserId(@Param("userId") Long userId);
	
	@Query("select u.userName , u.introduction  ,u.profilePicture ,u.id   from Follow f,User u where f.followId=:followId and f.userId = u.id and f.status='follow'")
	List<String> findFollowedUserByFollowId(@Param("followId") String followId);
	
	Integer countByUserIdAndFollowIdAndStatus(Long userId,String followId,String status);
	
	Follow findByUserIdAndFollowId(Long userId,String followId);
	
	@Modifying(clearAutomatically=true)
	@Transactional
	@Query("update Follow set status=?1,lastModifyTime=?2 where id=?3")
	Integer updateStatusById(String status,Long lastModifyTime,Long id);

}