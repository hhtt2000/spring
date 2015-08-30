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
	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CommentController.class);
	
	@RequestMapping(value="/comment/{id}", method=RequestMethod.GET)
	public @ResponseBody HashMap<String, ArrayList<CommentDto>> getComment(@PathVariable("id") int id) {
		ArrayList<CommentDto> comments = commentService.getComment(id);
		
		HashMap<String, ArrayList<CommentDto>> map = new HashMap<String, ArrayList<CommentDto>>();
		map.put("comments", comments);
		
		return map;
	}
	
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public @ResponseBody String newComment(CommentDto commentDto) {
		int result = commentService.insertComment(commentDto);
		
		return Integer.toString(result);
	}
	
	
	
	
}
