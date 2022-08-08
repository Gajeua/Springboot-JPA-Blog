package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해 Bean에 등록 해줌.
@Service
public class BoardService {
	
	@Autowired // DI를 사용.
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional // 하나의 트랜잭션으로 묶여서 전체가 실행. 성공하면 commit 실패하면 rollback
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("찾으시는 게시글이 없습니다.")
				);
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestboard) {
		Board board = boardRepository.findById(id).orElseThrow(
				()->{
					return new IllegalArgumentException("찾으시는 게시글이 없습니다.");
				});  // 영속화 완료
		board.setTitle(requestboard.getTitle());
		board.setContent(requestboard.getContent());
		// 해당 함수로 종료시에(Service가 종료될 때)  트랜잭션이 종료. 이때 더티체킹 - 자동 업데이트가 됨.  DB flush
				
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
	replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}
