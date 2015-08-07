package com.jjh.blueberry.controller;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@RequestMapping("/board")
@Controller
public class BbsController {
	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(BbsController.class);
	
	@RequestMapping("/newText")
	public String newText(Model model){
		//session id값을 얻는 과정
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername();
		
		BoardDao boardDao = new BoardDao();
		String name = boardDao.getBoardUserName(userId);
		model.addAttribute("name", name);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		model.addAttribute("categories", categories);
		return "newText";
	}
	
	@RequestMapping(value="/newText", method=RequestMethod.POST)
	public String newTextProcess(Model model, BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();
		int result = boardDao.insertText(boardDto);
		if(result == 1){
			return "redirect:/main";			
		} else {
			model.addAttribute("msg", "글작성이 완료되지 않았습니다.");
			return "newText";
		}
	}
	
	@RequestMapping("/updateText/id/{id}")
	public String updateText(@PathVariable("id") int id, Model model){
		BoardDao boardDao = new BoardDao();
		BoardDto dto = boardDao.selectOne(id);
		model.addAttribute("boardDto", dto);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		log.debug(categories);
		model.addAttribute("categories", categories);
		
		return "updateText";
	}
	
	@RequestMapping(value="/updateText", method=RequestMethod.POST)
	public String updateProcess(@ModelAttribute BoardDto boardDto) {
		BoardDao boardDao = new BoardDao();
		int result = boardDao.updateText(boardDto);
		if(result == 1) {
			return "redirect:/main";
		} else {
			int id = boardDto.getId();
			return "forward:updateText/id/"+id;			
		}
	}
	
	@RequestMapping("/deleteText/id/{id}")
	public String deleteText(@PathVariable("id") int id){
		BoardDao boardDao = new BoardDao();
		boardDao.deleteText(id);
		return "redirect:/main";
	}
	
}
