package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;


// JSP에서 DAO 역할
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);


}

// 전통적인 로그인 방식
// JPA Naming 쿼리 전략
// SELECT & FROM user WHERE username = ?1 AND password = ?2; 
//User findByUsernameAndPassword(String username, String password);

//아래 nativeQuery 방법도 있다.
//@Query(value = "SELECT & FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password);