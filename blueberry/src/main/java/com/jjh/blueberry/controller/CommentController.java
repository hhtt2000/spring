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

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/comment/{id}", method=RequestMethod.GET)
	public @ResponseBody HashMap<String, ArrayList<CommentDto>> getComment(@PathVariable("id") int id) {
		ArrayList<CommentDto> comments = commentService.getComment(id);
		
		HashMap<String, ArrayList<CommentDto>> map = new HashMap<String, ArrayList<CommentDto>>();
		map.put("comments", comments);
		
		return map;
	}
	
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, Integer> newComment(CommentDto commentDto) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int result = commentService.insertComment(commentDto);
		//댓글 개수 받아서 main.jsp ajax post에 같이 전달
		int postId = commentDto.getPostid();
		int commentNo = commentService.getCommentNo(postId);
		map.put("result", result);
		map.put("commentNo", commentNo);
		
		return map;
	}
		
	
}
