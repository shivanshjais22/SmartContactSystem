package com.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.User.User;

@Repository
public interface UserRepository  extends JpaRepository<	User, Integer> {

	  // Custom query to find user by email (username)
    @Query("select u from User u where u.email =:email")
    User getUserbyname(@Param("email") String email);
}
