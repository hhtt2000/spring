package com.jjh.blueberry.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.jjh.blueberry.common.exception.UserDuplicatedException;
import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.AccountDto;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;
import com.jjh.blueberry.dto.PagingDto;

@Service
@Transactional
public class HomeService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BCryptPasswordEncoder passwdEncoder;
	
	public void addAccount(AccountDto account) {
		String userId = account.getUserid();
		if(boardDao.findIfUserExistByGivenUserId(userId) >= 1) {
			throw new UserDuplicatedException(userId);
		}
		account.setPassword(passwdEncoder.encode(account.getPassword()));
		boardDao.addAccount(account);
		boardDao.addRoles(userId);
	}

	public Model getBoardList(int page, Model model){
		int totalCount = boardDao.getTotalCount();
		PagingDto paging = new PagingDto(page, totalCount);
		//카테고리, DB row 어디서부터, 몇개
		ArrayList<BoardDto> list = boardDao.getList(null, paging.getFromRowNum(), paging.getCountList());
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return model;
	}
	
	public ModelAndView getBoardListBySearch(String searchText, int page){
		ModelAndView model = new ModelAndView();
		
		int totalCount = boardDao.getSearchListCount(searchText);
		PagingDto paging = new PagingDto(page, totalCount);
		ArrayList<BoardDto> searchList = boardDao.getSearchList(searchText, paging.getFromRowNum(), paging.getCountList());
		
		model.addObject("paging", paging);
		model.addObject("list", searchList);
		return model;
	}
	
	public Model getBoardListByCategory(String category, int page, Model model){
		int totalCount = boardDao.getTotalCount(category);
		PagingDto paging = new PagingDto(page, totalCount);
		ArrayList<BoardDto> list = boardDao.getList(category, paging.getFromRowNum(), paging.getCountList());
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return model;
	}

	public ArrayList<CategoryDto> getCategoryList() {
		return boardDao.getCategories();
	}

	public void addCategory(CategoryDto categoryDto) {
		boardDao.insertCategory(categoryDto);
	}

	public Model getBoardByPostid(int postid, Model model) {
		PagingDto paging = new PagingDto(1, 1);
		BoardDto board = boardDao.selectOne(postid);
		ArrayList<BoardDto> list = new ArrayList<>();
		list.add(board);
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return model;
	}
	
	public String getServerTime(Locale locale) {
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);
		
		return formattedDate;
	}

}
