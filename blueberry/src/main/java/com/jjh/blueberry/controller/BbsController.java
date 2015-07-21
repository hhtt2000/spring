package com.jjh.blueberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjh.blueberry.dto.TextDto;

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
	
	@RequestMapping(value="/newTextProcessing.jh", method=RequestMethod.POST)
	public String newTextProcessing(Model model, TextDto textDto) {
		model.addAttribute("text", textDto);
		return "forward:/main.jh";
	}
}
