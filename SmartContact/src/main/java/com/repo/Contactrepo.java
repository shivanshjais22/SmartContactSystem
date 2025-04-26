package com.repo;

import java.awt.Container;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import com.example.User.Contact;

public interface Contactrepo extends JpaRepository<Contact,Integer> {

	
	@Query("from Contact as c where c.user.id = :id")
	public Page<Contact> findContactbyuser(@Param("id") int id,Pageable p);
//pagebel having to term which per  page contect page
	
}
