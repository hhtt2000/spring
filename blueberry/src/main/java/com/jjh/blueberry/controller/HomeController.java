package com.jjh.blueberry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jjh.blueberry.dto.CategoryDto;
import com.jjh.blueberry.service.HomeService;

/**
 * Handles requests for the application home page.
 */
@RequestMapping(value={"/", "/main"})
@Controller
public class HomeController {
	
	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping
	public String main(Model model){

		return "redirect:/main/1";
	}
	
	@RequestMapping("/{page}")
	public String mainPerPage(@PathVariable("page") int page, Model model){
		model = homeService.getBoardList(page, model);
		return "main";
	}
	
	@RequestMapping("/search/{searchText}/{page}")
	public ModelAndView searchText(@PathVariable("searchText") String searchText, @PathVariable("page") int page){
		ModelAndView model = new ModelAndView();

		model = homeService.getBoardListBySearch(searchText, page);
		model.setViewName("main");
		
		return model;
	}
	
	@RequestMapping("/category/{category}/{page}")
	public String categorizedMain(@PathVariable("category") String category, 
			@PathVariable("page") int page, Model model){
		model = homeService.getBoardListByCategory(category, page, model);
		
		return "main";
	}
	
	@RequestMapping("/categoryList")
	public @ResponseBody HashMap<String, ArrayList<CategoryDto>> getCategoryList() {
		HashMap<String, ArrayList<CategoryDto>> map = new HashMap<String, ArrayList<CategoryDto>>();
		
		ArrayList<CategoryDto> categories = homeService.getCategoryList();
		map.put("categories", categories);
		
		return map;	
	}
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public String addCategory(CategoryDto categoryDto) {
		homeService.addCategory(categoryDto);
		
		return "redirect:/main";			
	}
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String formattedDate = homeService.getServerTime(locale);

		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/getTime", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, String> getTime(Locale locale, Model model){
		HashMap<String, String> map = new HashMap<String, String>();
		
		String formattedDate = homeService.getServerTime(locale);	
		map.put("serverTime", formattedDate);
		
		return map;
	}
	
	@RequestMapping("/testsql")
	public String testsql(Model model){
		return "testsql";
	}
	
}
