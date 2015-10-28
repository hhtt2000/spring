package com.example.accounts;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.commons.ErrorResponse;

@RestController
public class AccountController {
	
	@Autowired
	AccountService service;
	
	@Autowired
	ModelMapper modelMapper;

	@RequestMapping("/hello")
	public String hello(){
		return "Hello, Spring Boot!!";
	}
	
	@RequestMapping(value="/accounts", method=RequestMethod.POST)
	public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
			BindingResult result){
		if(result.hasErrors()){
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("잘못된 요청입니다.");
			errorResponse.setCode("bad.request");
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		Account newAccount = service.createAccount(create);
		return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
	}
	
	@ExceptionHandler(UserDuplicatedException.class)
	public ResponseEntity<ErrorResponse> handleUserDuplicatedException (UserDuplicatedException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getUsername()+"(은)는 중복된 username 입니다.");
		errorResponse.setCode("username.duplicated.exception");
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
