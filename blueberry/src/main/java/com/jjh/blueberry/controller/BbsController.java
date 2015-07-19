package com.jjh.blueberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
