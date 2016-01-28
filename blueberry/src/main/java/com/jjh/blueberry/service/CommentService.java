package com.jjh.blueberry.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjh.blueberry.dao.CommentDao;
import com.jjh.blueberry.dto.CommentDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	public ArrayList<CommentDto> getComments(int postid) {
		return commentDao.getComments(postid);
	}

	public int insertComment(CommentDto commentDto) {
		String encodedPasswd = bcryptPasswordEncoder.encode(commentDto.getPassword());
		commentDto.setPassword(encodedPasswd);
		//Need transaction
		int result = commentDao.insertComment(commentDto);
		int postId = commentDto.getPostid();
		commentDao.updateCommentNo(postId);
		return result;
	}

	public int getCommentNo(int postId) {
		return commentDao.getCommentNo(postId);
	}

	public ArrayList<CommentDto> getRecentComments() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String now = sdf.format(calendar.getTime());
		return commentDao.getRecentComments(now);
	}

}
