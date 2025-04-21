package com.example.demo;

import java.security.Principal;

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

  //  private final SmartContactApplication smartContactApplication;
	
    @Autowired
    private BCryptPasswordEncoder pass;
    
	@Autowired
    private	UserRepository u;


	// thise for index page 
	@GetMapping("/")
	public String str(Model m) {
		m.addAttribute("title","disha shivansh jais ");
		return "index";
	}
	

	@GetMapping("/about")
	public String about(Model m) {
		m.addAttribute("title","disha shivansh jais ");
		return "about";
	}
	
	
	@GetMapping("/log")
	public String showLoginPage() {

		
	    return " login";  // Ensure this matches the template name without the ".html"
	}

	@GetMapping("/signup")
	public String signup(Model m) {
	    m.addAttribute("user", new User());
	    return "signup"; // matches 'signup.html'
	}
	
	
	@PostMapping("/do_register")  
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
	 u.setEnabled(true);
		this.u.save(u);
		//u.setPassword(pass.encode(u.getPassword()));
		System.out.println("done work");
		
		h.setAttribute("h1", new Message("success fulll!!","alert-error"));
		
		
		
		}
		catch (Exception e) {
		e.printStackTrace();
		m.addAttribute("exception", e);
		h.setAttribute("h1", new Message("masasde went wrong !!","alert-error"));
		return "/signup";
		}
		
		return "redirect:/signup";
		
	}
	
	
	@GetMapping("/user/index")
	public String userDashboard(Model model,Principal p) {
		
		 String Username=p.getName();
			System.out.println(Username);
	User user=u.findByEmail(Username);
		//	System.out.println(p.getClass().getName());
	    model.addAttribute("user", user);
	    return "user/index"; // This should be src/main/resources/templates/user/index.html
	}
	
	
}
