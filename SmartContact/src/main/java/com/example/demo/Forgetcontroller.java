package com.example.demo;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.User.User;
import com.repo.Contactrepo;
import com.repo.UserRepository;

import jakarta.servlet.http.HttpSession;
import  Email.Emailservices;
@Controller
public class Forgetcontroller {
	
	

	@Autowired
	private Contactrepo cr;
	
	@Autowired
	private Emailservices emailserver;
	
	@Autowired
	private UserRepository u;
	
	
	@Autowired
	 private BCryptPasswordEncoder b;
	
	
	@GetMapping("/forget")
	public String openEmail() {
		return "/forget/forget1";
	}
	
	@PostMapping("/forgetemail")
	public String getemail(@RequestParam ("email") String email,HttpSession session ) {
		
		
		
		
User user=this.u.findByEmail(email);
		


		
	Random random=new Random(1000); 
	int ans=	random.nextInt(999999);
		System.out.println(ans);
		

		
		String Scm ="OTP FROM SEM";
		String massage="<h1>YOUR OTP ID :- </h1>"+ans;
		
	boolean  falg=	this.emailserver.sendemail(massage, Scm, email);
		
	
	if(falg) {	
		
		session.setAttribute("myotp", ans);
		session.setAttribute("email", email);
		
		session.setAttribute("message", "OTP sent to your email.");

		
	
		return "/forget/otpcheck";
		
		
	}
	
	session.setAttribute("message", "Check you Email is not proper");

	  	return "/forget/forget1";
	
	}
	
	
	@PostMapping("/sendotp")
	public String verifiec(@RequestParam ("otp") int otp,HttpSession session) {
		
		Integer myotpObj = (Integer) session.getAttribute("myotp");
		String email = (String) session.getAttribute("email");

		if (myotpObj == null || email == null) {
		    session.setAttribute("message", "Session expired or invalid access.");
		    return "/forget/forget1"; // redirect to start of flow
		}

		int myotp = myotpObj;

		if (otp == myotp) {
		    return "/forget/new_passwordpage";
		} else {
		    session.setAttribute("message", "OTP wrong");
		    return "/forget/otpcheck";
		}

	}
	
	
	
	
	@PostMapping("/changepass")
	public String changepass1(@RequestParam ("np") String newpass,HttpSession session) {
		
		 String email = (String) session.getAttribute("email");

		    if (email == null) {
		        session.setAttribute("message", "Session expired. Please try again.");
		        return "/forget/forget1"; // redirect to restart the flow
		    }

		    User user = this.u.findByEmail(email);
		    if (user != null) {
		        user.setPassword(b.encode(newpass));
		        u.save(user);
		        session.invalidate(); // optional: clean up session after password reset
		        return "redirect:/log"; // or wherever your login page is
		    } else {
		        session.setAttribute("message", "No user found.");
		        return "/forget/new_passwordpage";
		    }
	}
}

