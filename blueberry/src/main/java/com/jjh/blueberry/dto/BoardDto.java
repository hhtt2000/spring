package com.jjh.blueberry.dto;

import java.sql.Date;

public class BoardDto {

	private int id;
	private String userid;
	private String name;
	private String title;

	private String content;
	private Date date;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "BoardDto [id=" + id + ", userid=" + userid + ", name=" + name + ", title=" + title + ", content="
				+ content + ", date=" + date + "]";
	}
}
