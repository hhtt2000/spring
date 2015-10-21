package com.example.accounts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
	
	@Id @GeneratedValue
	private long id;
	
	private String loginId;
	
	private String password;
}
