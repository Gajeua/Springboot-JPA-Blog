package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice  // 어디서든 Exception이 발생하면 여기 클래스로 온다.
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value=Exception.class) // 최상위 보모인 Exception으로 어떤 Exception이든 여기로 온다. 
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}
	
}
