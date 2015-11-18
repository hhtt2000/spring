package com.jjh.blueberry.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {

	private String name;
	private String password;
	private String content;
	private Date regdate;
	private int postid;
}
