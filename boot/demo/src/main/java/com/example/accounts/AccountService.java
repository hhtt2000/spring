package com.example.accounts;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.accounts.AccountDto.Create;
import com.example.accounts.AccountDto.Update;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
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
			log.error("user duplicated exception occured. {}", username);
			throw new UserDuplicatedException(username);
		}
		Account account = modelMapper.map(dto, Account.class);
		
		Date now = new Date();
		account.setJoined(now);
		account.setUpdated(now);
		
		return repository.save(account);
	}

	public Account updateAccount(Long id, Update updateDto) {
		Account account = getAccount(id);
		account.setPassword(updateDto.getPassword());
		account.setFullName(updateDto.getFullName());
		return repository.save(account);
	}

	private Account getAccount(Long id) {
		Account account = repository.getOne(id);
		if(account == null) {
			throw new AccountNotFoundException(id);
		}
		return account;
	}

	public void deleteAccount(Long id) {
		repository.delete(getAccount(id));
	}
}
