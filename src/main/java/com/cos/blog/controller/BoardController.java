package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	

	// http://localhost:8000/blog
	@GetMapping("/")
	public String index(@AuthenticationPrincipal PrincipalDetail principal) {
		
		// yml파일로 경로를 설정해서  /WEB-INF/views/index.jsp 으로 이동
		System.out.print("로그인 사용자 아이디 : " +principal);
		return "index";
	}
}
