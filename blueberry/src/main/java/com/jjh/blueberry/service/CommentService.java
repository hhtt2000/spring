package com.jjh.blueberry.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjh.blueberry.dao.CommentDao;
import com.jjh.blueberry.dao.UserDao;
import com.jjh.blueberry.dto.AccountDto;
import com.jjh.blueberry.dto.CommentDto;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

@Service
@Transactional
public class CommentService {
	
	@Autowired
	private CommentDao commentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	public ArrayList<CommentDto> getComments(int postid) {
		return commentDao.getComments(postid);
	}

	public int insertComment(CommentDto commentDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String sessionUserName = auth.getName();
		XssSaxFilter filter = XssSaxFilter.getInstance();
		if("anonymousUser".equals(sessionUserName)) {
			commentDto.setName(filter.doFilter(commentDto.getName()));
			String encodedPasswd = bcryptPasswordEncoder.encode(commentDto.getPassword());
			commentDto.setPassword(encodedPasswd);
		}else {
			AccountDto user = userDao.getUserInfoById(sessionUserName);
			commentDto.setName(user.getName());
			commentDto.setPassword(user.getPassword());
		}
		commentDto.setContent(filter.doFilter(commentDto.getContent()));
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
