package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
		
	// 로그인 페이지
	@GetMapping(value="/oauth2Login")
	public String loginPage() {
			
		return "index.html";
    }
	
		

}
