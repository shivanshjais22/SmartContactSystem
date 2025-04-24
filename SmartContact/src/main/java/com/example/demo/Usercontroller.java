package com.example.demo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import com.springsecurity.UserDetailServiceImp;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.User.Contact;
import com.example.User.User;
import com.example.demo.help.Message;
import com.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class Usercontroller {

    private final Controller1 controller1;

    private final UserDetailServiceImp userDetailServiceImp;
	
	@Autowired
    private	UserRepository u;


    Usercontroller(UserDetailServiceImp userDetailServiceImp, Controller1 controller1) {
        this.userDetailServiceImp = userDetailServiceImp;
        this.controller1 = controller1;
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
	public String userDashboard(Model model ,Principal p) {
		 return "user/index1"; // This should be src/main/resources/templates/user/index.html
	}
	
	
	@GetMapping("/add")
	public String Usersideinfo(Model m) {
		m.addAttribute("title", "tere ri aake ye ");
		m.addAttribute("contact", new Contact());
		return "user/add_contact";
	}
	
	@PostMapping("/post-contact")
	public String process(@ModelAttribute Contact contact,@RequestParam("p") MultipartFile  File,Principal p,HttpSession h1) {
	

		try {
		
	String str=	p.getName();
	User user=u.findByEmail(str);
	
	contact.setUser(user);
	
	user.getContact().add(contact);
	
	
	// file process
	
	if(File.isEmpty()) {
		
		
	}else {
		
		contact.setImage(File.getOriginalFilename());
		
	File f=	new ClassPathResource("static/img").getFile();
  
Path p1=	Paths.get(f.getAbsolutePath()+f.separator+File.getOriginalFilename());
	 
	Files.copy(File.getInputStream(),p1,StandardCopyOption.REPLACE_EXISTING);	

System.out.println("heyyyy");
	}
	
	
	
	
	this.u.save(user);
		System.out.println(contact);
		
		
		System.out.println("egyuergyfueyghdge");
	
		
		 h1.setAttribute("message", new Message("Your Contact is added !! Add more", "success"));	}catch (Exception e) {
			System.out.println(e.getMessage());
			h1.setAttribute("message", new Message("Something went wrong while adding contact", "danger"));
	}
		
		return "/user/add_contact";
	}
		
		

}
