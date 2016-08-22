package com.favorites.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CollectRepository extends JpaRepository<Collect, Long> {

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
	
	
	@Query("select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id and c.userId=?1 ")
	Page<CollectView> findViewByUserId(Long userId,Pageable pageable);
	
	
	@Query("select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id and c.favoritesId=?1 ")
	Page<CollectView> findViewByFavoritesId(Long favoritesId,Pageable pageable);
	
	
	@Query("select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id ")
	Page<CollectView> findAllView(Pageable pageable);
	
	
}