package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	// 인터넷 브라우저 요청은 무조건 GET 요청만 가능.
	// http://localhost:8080/http/get
	@GetMapping("/http/get")
	public String getTest(Member mem) {// id=1&username=Lee&password=1234&email=fjqmdls22@naver.com
		return "get 요청 : "+mem.getId()+","+mem.getUsername()+","+mem.getPassword()+","+mem.getEmail();
	}
	
	// http://localhost:8080/http/post
	@PostMapping("/http/post")
	public String postTest(@RequestBody String text) {
		return "post 요청"+text;
	}
	
	// http://localhost:8080/http/put
	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}
	
	// http://localhost:8080/http/delete
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	

}
