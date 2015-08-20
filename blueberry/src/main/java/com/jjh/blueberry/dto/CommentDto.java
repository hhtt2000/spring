package com.jjh.blueberry.dto;

import java.sql.Date;

public class CommentDto {

	private String name;
	private String password;
	private String content;
	private Date regdate;
	private int postid;

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getContent() {
		return content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public int getPostid() {
		return postid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	@Override
	public String toString() {
		return "CommentDto [name=" + name + ", password=" + password + ", content=" + content + ", regdate=" + regdate
				+ ", postid=" + postid + "]";
	}

}
