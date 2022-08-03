package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 해줌.
@Service
public class UserService {
	
	@Autowired // DI를 사용.
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // 하나의 트랜잭션으로 묶여서 전체가 실행. 성공하면 commit 실패하면 rollback
	public void 회원가입(User user) {
		

		String rawPassword = user.getPassword(); // 원래 암호.
		String encPassword = encoder.encode(rawPassword); // 해쉬한 암호.
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(int id, User user) {
		User persistance = userRepository.findById(id).orElseThrow(
				()-> {return new IllegalArgumentException("회원 찾기 실패"); 
				});
		String rawPassword=user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		
	}
	
}

// 전통적인 로그인 방식
//@Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
//public User 로그인(User user) {
//	return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//}
