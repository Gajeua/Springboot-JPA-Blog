package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 해줌.
@Service
public class BoardService {
	
	@Autowired // DI를 사용.
	private BoardRepository boardRepository;
	
	
	@Transactional // 하나의 트랜잭션으로 묶여서 전체가 실행. 성공하면 commit 실패하면 rollback
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("찾으시는 게시글이 없습니다.")
				);
	}
	
}
