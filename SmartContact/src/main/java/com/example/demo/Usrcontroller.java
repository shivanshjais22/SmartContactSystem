package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class Usrcontroller {
	
	@RequestMapping("/index")
	public String dashboard() {
		return  "normal/user_dashboard";
	}

}
