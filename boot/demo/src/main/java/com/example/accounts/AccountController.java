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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	// /accounts?page=0&size=20&sort=username&sort=joined,desc
	//로 들어오는 값을 pagable에서 받아줌.
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public PageImpl<Response> getAccounts(Pageable pageable) {
		Page<Account> page = repository.findAll(pageable);
		List<Response> content = page.getContent().stream()
		                 .map(account -> modelMapper.map(account, AccountDto.Response.class))
		                 .collect(Collectors.toList());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}
	
	@RequestMapping(value="/accounts/{id}", method=RequestMethod.GET)
	public ResponseEntity getAccount(@PathVariable Long id) {
		Account account = repository.findOne(id);
		if(account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Response result = modelMapper.map(account, AccountDto.Response.class);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//전체 업데이트: PUT
	//부분 업데이트: PATCH
	@RequestMapping(value="/accounts/{id}", method=RequestMethod.PUT)
	public ResponseEntity updateAccount(@PathVariable Long id, 
			@RequestBody @Valid AccountDto.Update updateDto,
			BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Account updatedAccount = service.updateAccount(id, updateDto);
		return new ResponseEntity<>(modelMapper.map(updatedAccount, AccountDto.Response.class),
				HttpStatus.OK);
	}
	
	@ExceptionHandler(UserDuplicatedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleUserDuplicatedException (UserDuplicatedException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getUsername()+"(은)는 중복된 username 입니다.");
		errorResponse.setCode("username.duplicated.exception");
		return errorResponse;
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getId()+"은(는) 존재하지 않는 아이디입니다.");
		errorResponse.setCode("account.not.found.exception");
		return errorResponse;
	}
}
