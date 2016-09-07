package com.favorites.domain;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.favorites.domain.enums.CollectType;
import com.favorites.domain.enums.IsDelete;

public interface CollectRepository extends JpaRepository<Collect, Long> {
	
	public String baseSql="select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,u.profilePicture as profilePicture,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id and c.isDelete='no'";
	
	public String isDeleteBaseSql="select c.id as id,c.title as title, c.type as type,c.url as url,c.logoUrl as logoUrl,c.userId as userId, "
			+ "c.remark as remark,c.description as description,c.lastModifyTime as lastModifyTime, "
			+ "u.userName as userName,u.profilePicture as profilePicture,f.id as favoriteId,f.name as favoriteName "
			+ "from Collect c,User u,Favorites f WHERE c.userId=u.id and c.favoritesId=f.id and c.isDelete='yes'";
	
	Long countByUserIdAndIsDelete(Long userId,IsDelete isDelete);
	
	Long countByUserIdAndTypeAndIsDelete(Long userId,CollectType type,IsDelete isDelete);
	
	Collect findByIdAndUserId(Long id,Long userId);
	 
	@Transactional
    Long deleteById(Long id);
	
	Page<Collect> findByFavoritesIdAndIsDelete(Long favoritesId,Pageable pageable,IsDelete isDelete);
	
	List<Collect> findByFavoritesIdAndIsDelete(Long favoritesId,IsDelete isDelete);
	
	List<Collect> findByFavoritesIdAndUrlAndUserIdAndIsDelete(Long favoritesId,String url,Long userId,IsDelete isDelete);
	
	@Transactional
	@Modifying
	@Query("update Collect c set c.type = ?1 where c.id = ?2 and c.userId=?3 ")
	int modifyByIdAndUserId(CollectType type, Long id, Long userId);
	
	@Transactional
	@Modifying
	@Query("delete from Collect where favoritesId = ?1")
	void deleteByFavoritesId(Long favoritesId);
	
	@Query(baseSql+ " and c.userId=?1 ")
	Page<CollectView> findViewByUserId(Long userId,Pageable pageable);
	
	@Query(isDeleteBaseSql+ " and c.userId=?1 ")
	Page<CollectView> findViewByUserIdAndIsDelete(Long userId,Pageable pageable);
	
	@Query(baseSql+ " and c.userId=?1 and c.type=?2")
	Page<CollectView> findViewByUserIdAndType(Long userId,Pageable pageable,CollectType type);
	
	@Query(baseSql+ " and c.userId=?1 and c.type=?2 and c.favoritesId=?3")
	Page<CollectView> findViewByUserIdAndTypeAndFavoritesId(Long userId,Pageable pageable,CollectType type,Long favoritesId);
	
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
	
	Long countByFavoritesIdAndTypeAndIsDelete(Long favoritesId,CollectType type,IsDelete isDelete);
	
	List<Collect> findByCreateTimeLessThanAndIsDeleteAndFavoritesIdIn(Long createTime,IsDelete isDelete,List<Long> favoritesIds);
	
	@Transactional
	@Modifying
	@Query("update Collect c set c.isDelete = ?1,c.lastModifyTime = ?2 where c.id = ?3")
	int modifyIsDeleteById(IsDelete isDelete,Long lastModifyTime,Long id);
}