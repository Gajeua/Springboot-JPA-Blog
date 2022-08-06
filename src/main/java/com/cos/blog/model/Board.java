package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;

	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob  // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인 되어서 글자 용량이 큼 
		
	@Column
	private int count; // 조회수
	
	@ManyToOne  // 연관관계 Many = Board, One = User
	@JoinColumn(name = "userId")
	private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)  //테이블에 생성하는 FK키가 아닌 Select할때 Join을 위해 mappedBy로 Reply의 board 사용.
	@JsonIgnoreProperties({"board"})  // 무한참조 방지 - (Board에서 Reply를 파싱할 때 Reply 안에있는 "board"는 다시 참조하지 않는다.)
	@OrderBy("id desc")
	private List<Reply> reply;
	
	@CreationTimestamp  // 데이터가 insert 혹은 update 될때 시간이 들어감.
	private Timestamp createDate;
	
}
