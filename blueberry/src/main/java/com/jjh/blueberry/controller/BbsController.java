package com.jjh.blueberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BbsController {

	public String list(Model model){
		
		return "list";
	}
}
