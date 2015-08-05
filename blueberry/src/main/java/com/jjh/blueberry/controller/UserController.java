package com.jjh.blueberry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value="error", required=false) String error) {
		ModelAndView model = new ModelAndView();
		if(error != null) {
			model.addObject("error", "로그인 실패!");
		}
		model.setViewName("login");
		
		return model;
	}
}
