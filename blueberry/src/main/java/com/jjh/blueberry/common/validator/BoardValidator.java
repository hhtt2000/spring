package com.jjh.blueberry.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.jjh.blueberry.dto.BoardDto;

public class BoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return BoardDto.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		BoardDto boardDto = (BoardDto) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		if(isEmptyOrWhitespace(boardDto.getContent())){
			errors.rejectValue("content", "required");
		}
		
		if(boardDto.getTitle() == null || boardDto.getTitle().length() > 50){
			System.out.println(boardDto.getTitle());
			System.out.println(boardDto.getTitle().length());
			errors.rejectValue("title", "50자 이하");
		}
	}

	private boolean isEmptyOrWhitespace(String content) {
		if(content == null || content.replaceAll("&nbsp;", " ").trim().length() == 0){
			return true;
		} else{
			return false;
		}
	}

}
