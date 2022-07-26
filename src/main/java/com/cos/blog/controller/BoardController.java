package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// http://localhost:8000/blog
	// yml파일로 경로를 설정해서  /WEB-INF/views/index.jsp 으로 이동
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("boards", boardService.글목록());

		return "index";  // viewResolver 작동 - index페이지로 model의 정보를 들고 이당한다.
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
