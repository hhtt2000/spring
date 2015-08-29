package com.jjh.blueberry.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@Service
public class BbsService {

	@Autowired
	private BoardDao boardDao;

	public Model getInfo4NewText(Model model) {
		//session id값을 얻는 과정
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername();
		
		String name = boardDao.getBoardUserName(userId);
		model.addAttribute("name", name);
		
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		model.addAttribute("categories", categories);
		return model;
	}

	public int insertText(BoardDto boardDto) {
		return boardDao.insertText(boardDto);
	}

	public BoardDto getInfo4UpdateText(int id) {
		return boardDao.selectOne(id);
	}

	public int updateText(BoardDto boardDto) {
		return boardDao.updateText(boardDto);
	}

	public int deleteText(int id) {
		return boardDao.deleteText(id);
	}
	
	
}
