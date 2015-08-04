package com.jjh.blueberry.dto;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDto {

	private String userId;
	private String name;
	private String passwd;
	private Collection<SimpleGrantedAuthority> roles;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Collection<SimpleGrantedAuthority> getRoles() {
		return roles;
	}
	public void setRoles(Collection<SimpleGrantedAuthority> roles) {
		this.roles = roles;
	}
	
	
}
