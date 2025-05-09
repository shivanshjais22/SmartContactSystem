package com.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.User.User;

@Repository
public interface UserRepository  extends JpaRepository<	User, Integer> {
	  User findByEmail(String email);
}
