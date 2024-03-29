package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired  // DI한다.
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	// 전통적인 로그인 방식을 위한 DI
//	@Autowired  // DI한다.
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {  // username, password, email
		System.out.println("UserApiController save 호출됨");
	
		userService.회원가입(user);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
		
	}
	
	@PutMapping("/user/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody User user){
		userService.회원수정(id, user);
		// 여기서 트랜잭션 종료 후 DB값은 변경, 세션값은 변경되지 않음.
		// 직접 Authentication 객체를 만든 후 값 변경.
		// 세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
				
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
