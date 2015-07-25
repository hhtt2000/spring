package com.jjh.blueberry.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjh.blueberry.dto.BoardDto;
import com.nhncorp.lucy.security.xss.XssFilter;

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
	
	@RequestMapping(value="/main.jh", method=RequestMethod.POST)
	public String newTextProcessing(Model model, BoardDto boardDto) {
		
		model.addAttribute("text", boardDto);
		return "main";
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
