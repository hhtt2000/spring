package com.jjh.blueberry.dto;

import javax.validation.constraints.Pattern;
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
	@Pattern(regexp="^[a-zA-Z][a-zA-Z0-9]+$", message="아이디는 영문자로 시작해야 하고, 한글과 특수문자는 사용할 수 없습니다.")
	private String userid;
	@NotBlank(message="이름을 입력해 주세요.")
	@Size(max=10, message="이름은 10자 이내로 입력해 주세요.")
	@Pattern(regexp="^[a-zA-Z0-9가-힣]+$", message="이름은 영어, 한글, 숫자만 사용할 수 있습니다.")
	private String name;
	@NotBlank(message="비밀번호를 입력해 주세요.")
	@Size(min=5, max=45, message="비밀번호는 5~45자로 입력해 주세요.")
	@Pattern(regexp="^[\\w@#$%&]+$", message="특수기호는 @, #, $, %, &를 사용할 수 있습니다.")
	private String password;	
}
