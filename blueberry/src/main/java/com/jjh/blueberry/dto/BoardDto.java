package com.jjh.blueberry.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDto {

	private int id;
	private String userid;
	private String name;
	private String title;
	private String content;
	private Date regdate;
	private String category;
	private int commentno;//댓글 개수
}
