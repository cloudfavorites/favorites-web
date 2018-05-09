package com.favorites.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.favorites.domain.Praise;
import com.favorites.domain.view.CommentView;

import javax.transaction.Transactional;

public interface PraiseRepository extends JpaRepository<Praise, Long> {


	Long countByCollectId(Long collectId);
	
	Praise findByUserIdAndCollectId(Long userId,Long collectId); 
	
	public String findPraiseUserSql="select u.id as userId,u.userName as userName,u.profilePicture as profilePicture,p.createTime as createTime "
			+ "from Praise p,User u WHERE p.userId=u.id";
	
	@Query(findPraiseUserSql+ " and p.id=?1")
	CommentView findPraiseUser(Long id);

	@Transactional
	void deleteById(Long id);
}