package com.jjh.blueberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;

@Controller
public class BbsController {
	
	@RequestMapping("/list.jh")
	public String list(Model model){
		
		return "list";
	}
	
	@RequestMapping("/newText.jh")
	public String newText(Model model){
		
		return "newText";
	}
	
	@RequestMapping(value="/insertProcessing.jh", method=RequestMethod.POST)
	public String newTextProcessing(Model model, BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();
		int result = boardDao.insertText(boardDto);
		if(result == 1){
			return "redirect:main.jh";			
		} else {
			return "forward:newText.jh";
		}
	}
	
	@RequestMapping("/updateText.jh")
	public String updateText(){
		return "updateText";
	}
	@RequestMapping("/deleteText.jh")
	public String deleteText(){
		return "main";
	}
}
