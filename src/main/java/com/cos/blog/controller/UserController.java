package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

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
	public String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
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
		
		// Gson, Json Simplem, ObjectMapper 라이브러리들이 있다.
		// 여기서 사용할 건 ObjectMapper 사용
		
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성 - 헤더에 타입 정해주기
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기 - 바디 데이터와 헤더값을 가지는 엔티티 생성
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",  // 토큰 발급 요청 주소
				HttpMethod.POST,  // 메소드 타입
				kakaoProfileRequest2,  // 데이터
				String.class  // 응답받을 타입 (String으로 받음)
		);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {
			kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) :"+kakaoProfile.getId());
		System.out.println("카카오 이메일 :"+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그 서버 카카오 유저네임 :"+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그 서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그 서버 패스워드 : "+cosKey);
		
		// 빌더 사용
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자인지 체크 해서 처리
		User originUser =  userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			userService.회원가입(kakaoUser);
		}
		System.out.println("자동 로그인을 진행합니다.");
		// 로그인 처리 (강제로 로그인 처리한 세션등록 로직을 가져옴)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "redirect:/";
	}
	
}
