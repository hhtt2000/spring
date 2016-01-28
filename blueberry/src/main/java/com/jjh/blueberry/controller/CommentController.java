package com.jjh.blueberry.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjh.blueberry.dto.CommentDto;
import com.jjh.blueberry.service.CommentService;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/comment/{postid}", method=RequestMethod.GET)
	public @ResponseBody HashMap<String, ArrayList<CommentDto>> getComment(@PathVariable("postid") int postid) {
		ArrayList<CommentDto> comments = commentService.getComments(postid);
		
		HashMap<String, ArrayList<CommentDto>> map = new HashMap<String, ArrayList<CommentDto>>();
		map.put("comments", comments);
		
		return map;
	}
	
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, Integer> newComment(CommentDto commentDto) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String name = commentDto.getName();
		String content = commentDto.getContent();
		XssSaxFilter filter = XssSaxFilter.getInstance();
		commentDto.setName(filter.doFilter(name));
		commentDto.setContent(filter.doFilter(content));
		int result = commentService.insertComment(commentDto);
		//댓글 개수 받아서 main.jsp ajax post에 같이 전달
		int postId = commentDto.getPostid();
		int commentNo = commentService.getCommentNo(postId);
		map.put("result", result);
		map.put("commentNo", commentNo);
		
		return map;
	}
	
	@RequestMapping("/recentComments")
	public @ResponseBody HashMap<String, ArrayList<CommentDto>> getRecentComments() {
		HashMap<String, ArrayList<CommentDto>> map = new HashMap<>();
		ArrayList<CommentDto> comments = commentService.getRecentComments();
		map.put("comments", comments);
		return map;
	}
	
}
