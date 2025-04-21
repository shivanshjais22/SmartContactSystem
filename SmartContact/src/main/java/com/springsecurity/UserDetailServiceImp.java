package com.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.User.User;
import com.repo.UserRepository;

@Service
public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	private UserRepository r;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User	l= r.findByEmail(username);
	if(l==null) {
		throw new UsernameNotFoundException("not found !!");
	}
	
	System.out.println("Logging in user: " + username);
	System.out.println("Found user: " + l.getEmail() + " with password: " + l.getPassword());

	CustomUserDetails u= new CustomUserDetails(l);
	
		return u;
	}

}
