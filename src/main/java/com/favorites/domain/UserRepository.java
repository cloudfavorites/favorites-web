package com.favorites.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    User findByUserNameOrEmail(String username, String email);

/*    @Query("from User u where u.name=:name")
    User findUser(@Param("name") String name);
    
*/
}