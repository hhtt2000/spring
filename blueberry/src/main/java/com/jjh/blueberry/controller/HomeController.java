package com.jjh.blueberry.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;
import com.jjh.blueberry.dto.PagingDto;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/main")
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HomeController.class);
	
	@Autowired
	private BoardDao boardDao;
	
	@RequestMapping
	public String main(Model model){

		return "redirect:/main/1";
	}
	
	@RequestMapping("/{page}")
	public String mainPerPage(@PathVariable("page") int page, Model model){
		PagingDto paging = new PagingDto(page);
		paging.setTotalCount(boardDao.getTotalCount(null));
		//limit 어디서부터, 몇개
		int fromRowNum = (paging.getCurPage()-1)*paging.getCountList();
		int countList = paging.getCountList();
		ArrayList<BoardDto> list = boardDao.getList(null, fromRowNum, countList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "main";
	}
	
	@RequestMapping("/category/{category}/{page}")
	public String categorizedMain(@PathVariable("category") String category, 
			@PathVariable("page") int page, Model model){
		PagingDto paging = new PagingDto(page);
		paging.setTotalCount(boardDao.getTotalCount(category));
		//limit 어디서부터, 몇개
		int fromRowNum = (paging.getCurPage()-1)*paging.getCountList();
		int countList = paging.getCountList();
		ArrayList<BoardDto> list = boardDao.getList(category, fromRowNum, countList);
		model.addAttribute("paging", paging);
		model.addAttribute("list", list);
		return "main";
	}
	
	@RequestMapping("/categoryList")
	public @ResponseBody HashMap<String, ArrayList<CategoryDto>> getCategoryList() {
		HashMap<String, ArrayList<CategoryDto>> map = new HashMap<String, ArrayList<CategoryDto>>();
		ArrayList<CategoryDto> categories = boardDao.getCategories();
		map.put("categories", categories);
		
		return map;	
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public String addCategory(CategoryDto categoryDto) {
		boardDao.insertCategory(categoryDto);
		return "redirect:/main";			
	}
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/getTime", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, String> getTime(Locale locale, Model model){
		HashMap<String, String> map = new HashMap<String, String>();
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);
		
		map.put("serverTime", formattedDate);
		
		return map;
	}
	
	@RequestMapping("/testsql")
	public String testsql(Model model){
		return "testsql";
	}
	
}
