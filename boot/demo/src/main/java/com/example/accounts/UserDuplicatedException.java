package com.example.accounts;

public class UserDuplicatedException extends RuntimeException {
	private String username;
	
	public UserDuplicatedException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
