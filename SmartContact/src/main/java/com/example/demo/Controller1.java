package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.User.User;
import com.example.demo.help.Message;
import com.repo.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Controller1 {

    private final SmartContactApplication smartContactApplication;
	
    @Autowired
    private BCryptPasswordEncoder pass;
    
	@Autowired
    private	UserRepository u;

    Controller1(SmartContactApplication smartContactApplication) {
        this.smartContactApplication = smartContactApplication;
    }

	@GetMapping("/")
	public String str(Model m) {
		m.addAttribute("title","disha shivansh jais ");
		return "index";
	}
	
	   @GetMapping("/login")
	    public String loginPage() {
	        return "login";  // Returning the login page view
	    }

	@GetMapping("/about")
	public String about(Model m) {
		m.addAttribute("title","disha shivansh jais ");
		return "about";
	}
	
	
	@GetMapping("/singup")
	public String sing(Model m) {
		m.addAttribute("user", new User());
		return "singup";
	}
	
	
	@PostMapping("/do_regester")
	public String registre(@Valid @ModelAttribute("user") User u,
            BindingResult result, // RIGHT AFTER the @Valid param
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
            Model m,
            HttpSession h) {
		
		
		try {
			
			if(!agreement) {
				System.out.println("you hav enot agreed the term and conditin ");
				throw  new Exception("you not good");
			}
			
			
			if(result.hasErrors())
			{
				System.out.println("hey i am tere"+ result.toString());
				m.addAttribute("user", u); 
				return "singup";
			
			}
			
		System.out.println(agreement);
		System.out.println(u.getEmail());
		System.out.println(u.getPassword());
		System.out.println(u.getAbout());
		u.setImageurl("defualt.jpg");
		u.setRole("USER");
		u.setEnabled(true);
		u.setPassword(pass.encode(u.getPassword()));
		this.u.save(u);
		u.setPassword(pass.encode(u.getPassword()));
		System.out.println("done work");
		
		h.setAttribute("h1", new Message("success fulll!!","alert-error"));
		
		
		
		}
		catch (Exception e) {
		e.printStackTrace();
		m.addAttribute("", e);
		h.setAttribute("h1", new Message("masasde went wrong !!","alert-error"));
		}
		
		return "singup";
	}
	
	
}
