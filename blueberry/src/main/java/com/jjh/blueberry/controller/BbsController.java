package com.jjh.blueberry.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjh.blueberry.common.validator.BoardValidator;
import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@RequestMapping("/board")
@Controller
public class BbsController {
	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BbsController.class);
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping("/newText")
	public String newText(Model model){
		//session id값을 얻는 과정
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername();
		
		String name = boardDao.getBoardUserName(userId);
		model.addAttribute("name", name);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		model.addAttribute("categories", categories);
		return "newText";
	}
	
	@RequestMapping(value="/newText", method=RequestMethod.POST)
	public String newTextProcess(Model model, BoardDto boardDto,
			BindingResult bindingResult) {
		model.addAttribute("boardDto", boardDto);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		model.addAttribute("categories", categories);
		
		new BoardValidator().validate(boardDto, bindingResult);
		if(bindingResult.hasErrors()){
			return "newText";
		} else {
			int result = boardDao.insertText(boardDto);
			if(result == 1){
				return "redirect:/main";			
			} else {
				return "newText";			
			}
		}
	}
	
	@RequestMapping("/updateText/id/{id}")
	public String updateText(@PathVariable("id") int id, Model model){
		BoardDto dto = boardDao.selectOne(id);
		model.addAttribute("boardDto", dto);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		model.addAttribute("categories", categories);
		
		return "updateText";
	}
	
	@RequestMapping(value="/updateText", method=RequestMethod.POST)
	public String updateProcess(@ModelAttribute BoardDto boardDto, BindingResult bindingResult) {
		int result = boardDao.updateText(boardDto);
		
		new BoardValidator().validate(boardDto, bindingResult);
		int id = boardDto.getId();
		if(bindingResult.hasErrors()){
			return "forward:updateText/id/"+id;
		}
		if(result == 1) {
			return "redirect:/main";
		} else {
			return "forward:updateText/id/"+id;			
		}
	}
	
	@RequestMapping("/deleteText/id/{id}")
	public String deleteText(@PathVariable("id") int id){
		boardDao.deleteText(id);
		return "redirect:/main";
	}
	
}
