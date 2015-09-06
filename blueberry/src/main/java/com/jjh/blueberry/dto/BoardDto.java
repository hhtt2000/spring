package com.jjh.blueberry.dto;

import java.sql.Date;

public class BoardDto {

	private int id;
	private String userid;
	private String name;
	private String title;
	private String content;
	private Date regdate;
	private String category;
	private int commentno;//댓글 개수

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getCommentno() {
		return commentno;
	}

	public void setCommentno(int commentno) {
		this.commentno = commentno;
	}

	@Override
	public String toString() {
		return "BoardDto [id=" + id + ", userid=" + userid + ", name=" + name + ", title=" + title + ", content="
				+ content + ", regdate=" + regdate + ", category=" + category + "]";
	}

	
}
