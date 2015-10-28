package com.example.accounts;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accounts.AccountDto.Create;

@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	public Account createAccount(Create dto) {
//		Account account = new Account();
//		account.setUsername(dto.getUsername());
//		account.setPassword(dto.getPassword());
		String username = dto.getUsername();
		if(repository.findByUsername(username) != null) {
			throw new UserDuplicatedException(username);
		}
		Account account = modelMapper.map(dto, Account.class);
		
		Date now = new Date();
		account.setJoined(now);
		account.setUpdated(now);
		
		return repository.save(account);
	}
}
