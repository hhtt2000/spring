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
			if(error.equals("dismatch")){
				model.addObject("error", "로그인 실패! 아이디와 비밀번호를 확인해주세요.");
			} else if(error.equals("timeout")) {
				model.addObject("error", "시간이 초과되었습니다.");
			}
		}
		model.setViewName("login");
		
		return model;
	}
}
