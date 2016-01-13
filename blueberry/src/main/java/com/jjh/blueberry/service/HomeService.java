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
		PagingDto paging = new PagingDto(page);
		paging.setTotalCount(boardDao.getTotalCount(null));
		//limit 어디서부터, 몇개
		int fromRowNum = (paging.getCurPage()-1)*paging.getCountList();
		int countList = paging.getCountList();
		ArrayList<BoardDto> list = boardDao.getList(null, fromRowNum, countList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		
		return model;
	}
	
	public ModelAndView getBoardListBySearch(String searchText, int page){
		ModelAndView model = new ModelAndView();
		
		int countSearchList = boardDao.getSearchListCount(searchText);
		PagingDto paging = new PagingDto(page);
		paging.setTotalCount(countSearchList);
		//limit 어디서부터, 몇개
		int fromRowNum = (paging.getCurPage()-1)*paging.getCountList();
		int countList = paging.getCountList();
		
		ArrayList<BoardDto> searchList = boardDao.getSearchList(searchText, fromRowNum, countList);
		
		model.addObject("paging", paging);
		model.addObject("list", searchList);
		
		return model;
	}
	
	public Model getBoardListByCategory(String category, int page, Model model){
		PagingDto paging = new PagingDto(page);
		paging.setTotalCount(boardDao.getTotalCount(category));
		//limit 어디서부터, 몇개
		int fromRowNum = (paging.getCurPage()-1)*paging.getCountList();
		int countList = paging.getCountList();
		ArrayList<BoardDto> list = boardDao.getList(category, fromRowNum, countList);
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

	public String getServerTime(Locale locale) {
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);
		
		return formattedDate;
	}
}
