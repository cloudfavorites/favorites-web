package com.favorites.domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Long countByCollectId(Long collectId);


}