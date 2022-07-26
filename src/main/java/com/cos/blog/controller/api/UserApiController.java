package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired  // DI한다.
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	// 전통적인 로그인 방식을 위한 DI
//	@Autowired  // DI한다.
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {  // username, password, email
		System.out.println("UserApiController save 호출됨");
	
		userService.회원가입(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	


}

// 전통적인 로그인 방식의 로직
//@PostMapping("/api/login")
//public ResponseDto<Integer> login(@RequestBody User user){ //, HttpSession session 매개변수에 적어도 DI가 된다.
//	System.out.println("UserApiController login 호출됨");
//	
//	User principal = userService.로그인(user); // principal 접근 주체
//	
//	// 세션 만들어주기
//	if(principal != null) {
//		session.setAttribute("principal", principal);
//	}else{
//		System.out.println("회원정보가 맞지 않습니다.");
//	}
//	
//	return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	
//}
