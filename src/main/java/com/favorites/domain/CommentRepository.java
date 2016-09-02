package com.favorites.domain;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Long countByCollectId(Long collectId);
	
	List<Comment> findByCollectIdOrderByIdDesc(Long collectId);
	
	@Transactional
    Long deleteById(Long id);

	public String findReplyUserSql="select u.id as userId,u.userName as userName,u.profilePicture as profilePicture,c.content as content,c.createTime as createTime "
			+ "from Comment c,User u WHERE c.userId=u.id";
	
	@Query(findReplyUserSql+ " and c.id=?1")
	CommentView findReplyUser(Long id);
	
}