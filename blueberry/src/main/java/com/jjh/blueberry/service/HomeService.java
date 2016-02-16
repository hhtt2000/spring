package com.jjh.blueberry.service;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.jjh.blueberry.common.exception.UserDuplicatedException;
import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dao.UserDao;
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
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwdEncoder;
	
	public void addAccount(AccountDto account) {
		String userId = account.getUserid();
		if(userDao.findIfUserExistByGivenUserId(userId) >= 1) {
			throw new UserDuplicatedException(userId);
		}
		account.setPassword(passwdEncoder.encode(account.getPassword()));
		userDao.addAccount(account);
		userDao.addRoles(userId);
	}

	public Model getBoardList(Model model, int page){
		int totalCount = boardDao.getTotalCount();
		PagingDto paging = new PagingDto(page, totalCount);
		//카테고리, DB row 어디서부터, 몇개
		ArrayList<BoardDto> list = boardDao.getList(null, paging.getFromRowNum(), paging.getCountList());
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return model;
	}
	
	public Model getBoardListBySearch(Model model, String searchText, int page){
		int totalCount = boardDao.getSearchListCount(searchText);
		PagingDto paging = new PagingDto(page, totalCount);
		ArrayList<BoardDto> searchList = boardDao.getSearchList(searchText, paging.getFromRowNum(), paging.getCountList());
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", searchList);
		return model;
	}
	
	public Model getBoardListByCategory(Model model, String category, int page){
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

	public Model getBoardByPostid(Model model, int postid) {
		PagingDto paging = new PagingDto(1, 1);
		BoardDto board = boardDao.selectOne(postid);
		ArrayList<BoardDto> list = new ArrayList<>();
		list.add(board);
		
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return model;
	}
	
	public int getRandomBoardNumber() {
		int total = boardDao.getTotalCount();
		Random random = new Random();
		//0 ~ (total - 1)
		int ranNum = random.nextInt(total);
		int postid = boardDao.getNthBoardId(ranNum);
		return postid;
	}
}
