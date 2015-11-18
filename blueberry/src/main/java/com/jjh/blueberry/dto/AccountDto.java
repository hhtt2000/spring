package com.jjh.blueberry.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {
	@NotBlank
	@Max(45)
	private String userid;
	@NotBlank
	@Max(10)
	private String name;
	@NotBlank
	@Min(5)
	@Max(45)
	private String password;	
}
