package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
	// 그냥 주소가 / 이면 index.jsp 허용
	// static 이하에 있는 /js/**, /css/**, /image/** 허용
	
	// http://localhost:8000/blog/user/joinForm
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		// /WEB-INF/views/user/joinForm.jsp
		return "user/joinForm";
	}
	
	// http://localhost:8000/blog/user/loginForm
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		// WEB-INF/views/user/loginForm.jsp
		return "user/loginForm";
	}

}
