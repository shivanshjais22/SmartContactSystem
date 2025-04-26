package com.example.demo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.springsecurity.Serveses;
import com.springsecurity.UserDetailServiceImp;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.User.Contact;
import com.example.User.User;
import com.example.demo.help.Message;
import com.repo.Contactrepo;
import com.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class Usercontroller {

    private final AuthenticationManager authenticationManager;

    private final Controller1 controller1;

    private final UserDetailServiceImp userDetailServiceImp;
	
	@Autowired
    private	UserRepository u;

	@Autowired
private Contactrepo cr;
	
	
    Usercontroller(UserDetailServiceImp userDetailServiceImp, Controller1 controller1, AuthenticationManager authenticationManager) {
        this.userDetailServiceImp = userDetailServiceImp;
        this.controller1 = controller1;
        this.authenticationManager = authenticationManager;
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
	
		 Serveses.removeMessage(); 
		try {
		
	String str=	p.getName();
	User user=u.findByEmail(str);
	
	contact.setUser(user);
	
	user.getContact().add(contact);
	
	
	// file process
	
	if(File.isEmpty()) {
		System.out.println("file is empty");
		contact.setImage("defult.jpg");
		
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
	
// show contact handal
	//per page n=5
	// current page n 
	
	@GetMapping("/view/{page}")
	public String showview(@PathVariable("page") Integer page,Model m,Principal p ) {
		
		String username=p.getName();
		
	 User use=u.findByEmail(username);
//m.addAttribute("u", use.getContact());	
		
Pageable p1 =	 PageRequest.of(page, 5);
		
	Page<Contact>alluser=cr.findContactbyuser(use.getId(),p1);
		
	m.addAttribute("all",  alluser);
	m.addAttribute("currentpage", page);
	m.addAttribute("total",alluser.getTotalPages());
		return "/user/show_contact";
	}
	
	
	// showing particull information
	
	
	@GetMapping("/{id}/contact")
	public String  showsingleinfo(@PathVariable("id")  Integer id,Model m ,Principal p) {
		System.out.println(id);
		
	
	Optional<Contact> c=	this.cr.findById(id);
			Contact c1=c.get();
		
			String username=p.getName();
		User user=	this.u.findByEmail(username);
		
		if(user.getId()==c1.getUser().getId()) { 
			m.addAttribute("info",c1);
		}
		return "/user/singel_contact";
	}
		
	@GetMapping("/delete/{id}")
	public String getdelete(@PathVariable("id") Integer id,Model m,Principal p,HttpSession h1) {
		
		Contact con=this.cr.findById(id).get();
		
		con.setUser(null);
		
		this.cr.delete(con);
		
		 h1.setAttribute("message",new Message("Contact delete successfully","success"));
			
		System.out.println("gwygf hguy ysguydtuytef ");
		
/*		Optional<Contact> c =cr.findById(id);
		String username=p.getName();
		
	
	Swal.fire({
  title: "Custom animation with Animate.css",
  showClass: {
    popup: `
      animate__animated
      animate__fadeInUp
      animate__faster
    `
  },
  hideClass: {
    popup: `
      animate__animated
      animate__fadeOutDown
      animate__faster
    `
  }
});
		
		User user=	this.u.findByEmail(username);
		c.get().setUser(null);
		if(user.getId()==c.get().getUser().getId()) { 
		 cr.delete(c.get());
		 h1.setAttribute("message",new Message("Contact delete successfully","success"));
		}
		
		return "redirect:/user/view/0"; */
		
		return "redirect:/user/view/0";

	}
	
	// update from
	
/*	@RequestMapping("/{id}/update")
	public String Update(@PathVariable("id") Integer id,Model m) {
		
Contact c=this.cr.findById(id).get();
m.addAttribute("newobject", c);
		
		return "/user/add_contact";
		
	}
	
	@PostMapping("/update-contact")
	public String updatestring(@ModelAttribute Contact con) {
		
		System.out.println(con);
		
		return "user/view/0";
	}
	
	what i think shiv up
	*/ 

}
