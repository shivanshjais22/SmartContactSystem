package com.example.demo;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.User.Contact;
import com.example.User.User;
import com.repo.Contactrepo;
import com.repo.UserRepository;

@RestController
public class Search {
	
@Autowired
private Contactrepo cr;

@Autowired
private UserRepository u;


@GetMapping("/serch/{query}")
public ResponseEntity<?> search(@PathVariable("query") String query,Principal p ){
	
	System.out.println(query);
	  
      User user=	u.findByEmail(p.getName());
	
List<Contact>contact=   cr.findByNameContainingAndUser(query,user);

return ResponseEntity.ok(contact);

}
}
