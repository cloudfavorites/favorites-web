package com.favorites.repository;

import com.favorites.domain.Letter;
import com.favorites.domain.view.LetterView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by DingYS on 2017/3/7.
 */
public interface LetterRepository extends JpaRepository<Letter,Long> {

    public String findSql = "select l.id as id,l.sendUserId as sendUserId,u.userName as sendUserName,u.profilePicture as profilePicture,l.content as content,l.createTime as createTime,l.pid as pid,l.type as type from Letter l,User u where l.sendUserId = u.id";

    @Transactional
    @Modifying
    @Query("update Letter l set l.pid = ?2 where l.id = ?1")
    int updatePidById(Long id,Long pid);

    @Query(findSql+" and l.receiveUserId = ?1")
    List<LetterView> findLetterByReceiveUserId(Long userId,Pageable pageable);

}
