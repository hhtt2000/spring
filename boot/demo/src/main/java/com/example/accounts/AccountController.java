package com.example.accounts;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.accounts.AccountDto.Response;
import com.example.commons.ErrorResponse;

@RestController
public class AccountController {
	
	@Autowired
	AccountService service;
	
	@Autowired
	AccountRepository repository;
	
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
	
	// /accounts?page=0&size=20&sort=username&sort=joined,desc
	//로 들어오는 값을 pagable에서 받아줌.
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	public ResponseEntity getAccounts(Pageable pageable) {
		Page<Account> page = repository.findAll(pageable);
		List<Response> content = page.getContent().stream()
		                 .map(account -> modelMapper.map(account, AccountDto.Response.class))
		                 .collect(Collectors.toList());
		PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
