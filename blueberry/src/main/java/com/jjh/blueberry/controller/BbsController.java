package com.jjh.blueberry.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@Controller
public class BbsController {
	
	@RequestMapping("/newText")
	public String newText(Model model){
		//session id값을 얻는 과정
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername();
		
		BoardDao boardDao = new BoardDao();
		String name = boardDao.getBoardUserName(userId);
		model.addAttribute("name", name);
		return "newText";
	}
	
	@RequestMapping(value="/insertProcess", method=RequestMethod.POST)
	public String newTextProcess(Model model, BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();
		int result = boardDao.insertText(boardDto);
		if(result == 1){
			return "redirect:main";			
		} else {
			return "forward:newText";
		}
	}
	
	@RequestMapping("/updateText/{id}")
	public String updateText(@PathVariable("id") int id, Model model){
		BoardDao boardDao = new BoardDao();
		BoardDto dto = boardDao.selectOne(id);
		model.addAttribute("boardDto", dto);
		return "updateText";
	}
	
	@RequestMapping(value="/updateProcess", method=RequestMethod.POST)
	public String updateProcess(@ModelAttribute BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();
		int result = boardDao.updateText(boardDto);
		if(result == 1) {
			return "redirect:main";
		} else {
			int id = boardDto.getId();
			return "forward:updateText/"+id;			
		}
	}
	
	@RequestMapping("/deleteText/{id}")
	public String deleteText(@PathVariable("id") int id){
		BoardDao boardDao = new BoardDao();
		boardDao.deleteText(id);
		return "redirect:/main";
	}
	
	@RequestMapping("/categoryList")
	public @ResponseBody HashMap<String, ArrayList<CategoryDto>> getCategoryList() {
		HashMap<String, ArrayList<CategoryDto>> map = new HashMap<String, ArrayList<CategoryDto>>();
		BoardDao boardDao = new BoardDao();
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		map.put("categories", categories);
		
		return map;	
	}
}
