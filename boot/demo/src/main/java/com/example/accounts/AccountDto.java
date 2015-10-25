package com.example.accounts;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

public class AccountDto {

	@Data
	public static class Create {
		@NotBlank
		@Size(min = 5)
		private String username;
		@NotBlank
		@Size(min = 5)
		private String password;
	}
	
	@Data
	public static class Response {
		private long id;
		private String username;
		private String fullName;
		private Date joined;
		private Date updated;
	}
}
