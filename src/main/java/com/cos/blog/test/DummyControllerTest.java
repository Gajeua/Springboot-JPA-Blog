package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DummyControllerTest {
	
	@Autowired  // 의존성 주입(DI)
	private final UserRepository userRepository;
	
	// http://localhost:8000/blog/dummy/user/1
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		 
		 return "삭제가 완료되었습니다. id : "+id;
	}
	//테스느
	// save 함수는 id를 전달하지 않으면 insert
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert
	// save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update
	// email, password
	// http://localhost:8000/blog/dummy/user/2
	@Transactional  // 함수 종료시에 자동 commit이 된다.
	@PutMapping("/dummy/user/{id}")
	//  json데이터를 요청 =>@RequestBody로 MessageConverter의 Jackson라이브러리가 Java Object로 변환해서 받아줌
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { 
		System.out.println("id :"+id);
		System.out.println("password :"+requestUser.getPassword());
		System.out.println("email :"+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("해당 아이디가 없습니다.")
		);
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// @Transactional 어노테이션을 붙이면 save를 안해도 update가 된다.
//		userRepository.save(user);
		return user;
	}
	
	
	//  http://localhost:8000/blog/dummy/users
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한 페이지 당 2건의 데이터를 리번 받아 볼 예정.
	//  http://localhost:8000/blog/dummy/user/page
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<User> pasingUsers = userRepository.findAll(pageable);
		List<User>users  = pasingUsers.getContent();
		return users;
	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	//  http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		// 만약 찾는 id가 없으면 null이므로 프로그램에 문제가 있으니 null일때의 행동을 넣어줘야 한다.
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(
//				() ->  new IllegalArgumentException("해당 아이디는 없습니다.")
//				);
//		return user;
//	}
		
		 User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});

		return user;
	} 
	
	
	// http://localhost:8000/blog/dummy/join  (요청)
	// http의 body에 username, passwordm, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {
		
		System.out.println("username :"+user.getUsername());
		System.out.println("password :"+user.getPassword());
		System.out.println("email :"+user.getEmail());
		
		user.setRole(RoleType.USER);
		try {
			userRepository.save(user);		
		}catch (Exception e) {
			// TODO: handle exception
			return "아이디를 확인하여 주십시오";
		}
		
		return "회원가입이 완료";
		

	}
}
