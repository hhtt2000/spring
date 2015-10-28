package com.example.commons;


import lombok.Data;

@Data
public class ErrorResponse {
	private String message;
	private String code;
	private java.util.List<FieldError> errors;
	
	public static class FieldError {
		private String field;
		private String value;
		private String reason;
	}
}
