package com.jjh.blueberry.common.exception;

public class UserDuplicatedException extends RuntimeException {

	private String userId;
	
	public UserDuplicatedException(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
}
