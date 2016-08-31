package com.favorites.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CollectRepository extends JpaRepository<Collect, Long> {
	
	public String baseSql="select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,u.profilePicture as profilePicture,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id";
	
	Long countByUserId(Long userId);
	
	Long countByUserIdAndType(Long userId,String type);
	
	Collect findByIdAndUserId(Long id,Long userId);
	 
	@Transactional
    Long deleteById(Long id);
	
	Page<Collect> findByFavoritesId(Long favoritesId,Pageable pageable);
	
	List<Collect> findByFavoritesId(Long favoritesId);
	
	List<Collect> findByFavoritesIdAndUrlAndUserId(Long favoritesId,String url,Long userId);
	
	@Transactional
	@Modifying
	@Query("update Collect c set c.type = ?1 where c.id = ?2")
	int modifyById(String type, long id);
	
	@Transactional
	@Modifying
	@Query("delete from Collect where favoritesId = ?1")
	void deleteByFavoritesId(Long favoritesId);
	
	@Query(baseSql+ " and c.userId=?1 ")
	Page<CollectView> findViewByUserId(Long userId,Pageable pageable);
	
	@Query(baseSql+ " and c.userId=?1 and c.type=?2")
	Page<CollectView> findViewByUserIdAndType(Long userId,Pageable pageable,String type);
	
	@Query(baseSql+ " and c.userId=?1 and c.type=?2 and c.favoritesId=?3")
	Page<CollectView> findViewByUserIdAndTypeAndFavoritesId(Long userId,Pageable pageable,String type,Long favoritesId);
	
	@Query(baseSql+ " and c.favoritesId=?1 ")
	Page<CollectView> findViewByFavoritesId(Long favoritesId,Pageable pageable);
	
	@Query(baseSql+ " and c.type='public' and c.userId!=?1 ")
	Page<CollectView> findExploreView(Long userId,Pageable pageable);
	
	@Query(baseSql+ " and (c.userId=?1 or ( c.userId in ?2 and c.type='public' )) ")
	Page<CollectView> findViewByUserIdAndFollows(Long userId,List<Long> userIds,Pageable pageable);
	
	
	@Query(baseSql+ " and c.userId=?1 and ( c.title like ?2 or c.description like ?2) ")
	Page<CollectView> searchMyByKey(Long userId,String key,Pageable pageable);
	
	@Query(baseSql+ " and c.type='public' and c.userId!=?1 and ( c.title like ?2 or c.description like ?2) ")
	Page<CollectView> searchOtherByKey(Long userId, String key,Pageable pageable);
	
	Long countByFavoritesIdAndType(Long favoritesId,String type);
}