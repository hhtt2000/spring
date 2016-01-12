package com.jjh.blueberry.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccountDto {
	@NotBlank(message="아이디를 입력해 주세요.")
	@Size(max=20, message="아이디는 20자 이내로 작성해 주세요.")
	private String userid;
	@NotBlank(message="이름을 입력해 주세요.")
	@Size(max=10, message="이름은 10자 이내로 입력해 주세요.")
	private String name;
	@NotBlank(message="비밀번호를 입력해 주세요.")
	@Size(min=5, max=45, message="비밀번호는 5~45자로 입력해 주세요.")
	private String password;	
}
