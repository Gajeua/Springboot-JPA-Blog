package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;

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
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		
		// WEB-INF/views/user/updateForm.jsp
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public @ResponseBody String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
		// POST방식으로 key=value 데이터를 요청
		// Retrofit2 (안드로이드에서 많이 사용)
		// OkHttp
		// RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성 - 헤더에 타입 정해주기
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성 - 바디에 파라미터 값 넣어주기
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "2dd172deb380f2d60e58b007a483b8cb");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기 - 바디 데이터와 헤더값을 가지는 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post방식으로 - response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",  // 토큰 발급 요청 주소
				HttpMethod.POST,  // 메소드 타입
				kakaoTokenRequest,  // 데이터
				String.class  // 응답받을 타입 (String으로 받음)
		);
		
		return "카카오 토큰 요청 완료 : 토큰 요청에 대한 응답 :"+response;
	}

}
