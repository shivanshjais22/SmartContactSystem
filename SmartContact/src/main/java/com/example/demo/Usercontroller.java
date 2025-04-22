package com.example.demo;

import java.security.Principal;
import com.springsecurity.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.User.Contact;
import com.example.User.User;
import com.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class Usercontroller {

    private final UserDetailServiceImp userDetailServiceImp;
	
	@Autowired
    private	UserRepository u;


    Usercontroller(UserDetailServiceImp userDetailServiceImp) {
        this.userDetailServiceImp = userDetailServiceImp;
    }
	
	
	@ModelAttribute
	public  void Addcooman(Model model,Principal p) {
		 String Username=p.getName();
			System.out.println(Username);
	User user=u.findByEmail(Username);
		//	System.out.println(p.getClass().getName());
	    model.addAttribute("user", user);

		
	}
	
	// home 
	@GetMapping("/index")
	public String userDashboard(Model model,Principal p) {
		 return "user/index"; // This should be src/main/resources/templates/user/index.html
	}
	
	
	@GetMapping("/add")
	public String Usersideinfo(Model m) {
		m.addAttribute("title", "tere ri aake ye ");
		m.addAttribute("contact", new Contact());
		return "user/add_contact";
	}
	
	@PostMapping("/post-contact")
	public String process(@ModelAttribute Contact contact,Principal p) {
	
	String str=	p.getName();
	User user=u.findByEmail(str);
	
	contact.setUser(user);
	
	user.getContact().add(contact);
	this.u.save(user);
		System.out.println(contact);
		
		
		System.out.println("egyuergyfueyghdge");
		
		return "user/index";
	}

}
