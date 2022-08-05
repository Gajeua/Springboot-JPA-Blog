package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor 
@Builder  // 빌더 패턴
@Entity // User 클래스로 MySQL에 테이블 생성 된다. ORM
// @DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {
	
	@Id  //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;  // 시퀀스, auto_increament
	
	@Column(nullable = false, length = 100, unique = true)
	private String username;  //아이디
	 
	@Column(nullable = false, length = 100)  // 해쉬로 비밀번호 암호화 예정
	private String password; 
	
	@Column(nullable = false, length = 50)  
	private String email;
	
	// @ColumnDefault("user")
	// DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role;  // Enum을 쓰는게 좋다. // ADMIN, USER 등 도메인을 정해줄 수 있다.
	
	private String oauth; // kakao, google
	
	@CreationTimestamp  // 시간이 자동 입력
	private Timestamp createDate;

}
