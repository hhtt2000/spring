package com.jjh.blueberry.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jjh.blueberry.dao.CommentDao;
import com.jjh.blueberry.dto.CommentDto;

@Service
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	public ArrayList<CommentDto> getComment(int id) {
		return commentDao.getComment(id);
	}

	public int insertComment(CommentDto commentDto) {
		String encodedPasswd = bcryptPasswordEncoder.encode(commentDto.getPassword());
		commentDto.setPassword(encodedPasswd);
		int result = commentDao.insertComment(commentDto);
		
		return result;
	}

}
