package com.jjh.blueberry.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jjh.blueberry.common.exception.UserDuplicatedException;
import com.jjh.blueberry.dto.AccountDto;
import com.jjh.blueberry.dto.CategoryDto;
import com.jjh.blueberry.service.HomeService;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

/**
 * Handles requests for the application home page.
 */
@RequestMapping(value={"/", "/main"})
@Controller
public class HomeController {
	
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
	
	@RequestMapping(value="/accounts", method=RequestMethod.GET)
	public String getCreateAccountView(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String sessionUserName = auth.getName();
		if("anonymousUser".equals(sessionUserName)) {
			return "account";
		} else {
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value="/accounts", method=RequestMethod.POST)
	public String createAccount(@Valid AccountDto account, BindingResult bindingResult, Model model) {
		model.addAttribute("account", account);
		if(bindingResult.hasErrors()) {
			return "account";
		} else {
			try{
				homeService.addAccount(account);
			} catch(UserDuplicatedException e) {
				String msg = e.getUserId()+"(은)는 이미 존재하는 아이디 입니다.";
				return setError(model, msg);
			} catch(RuntimeException e) {
				String msg = "예기치 않은 오류로 계정이 생성되지 않았습니다.";
				return setError(model, msg);
			}
			return "redirect:/main";
		}
	}

	private String setError(Model model, String msg) {
		model.addAttribute("error", msg);
		return "account";
	}
	
	@RequestMapping("/search/{searchText}/{page}")
	public ModelAndView searchText(@PathVariable("searchText") String searchText, @PathVariable("page") int page){
		XssSaxFilter filter = XssSaxFilter.getInstance();
		String cleanedSearchText = filter.doFilter(searchText);
		ModelAndView model = new ModelAndView();

		model = homeService.getBoardListBySearch(cleanedSearchText, page);
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
		XssSaxFilter filter = XssSaxFilter.getInstance();
		String name = categoryDto.getName();
		categoryDto.setName(filter.doFilter(name));
		homeService.addCategory(categoryDto);
		
		return "redirect:/main";			
	}
	
	@RequestMapping(value = "/todo", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String formattedDate = homeService.getServerTime(locale);

		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/epl", method = RequestMethod.GET)
	public String epl() {
		
		return "epl";
	}
	
	@RequestMapping("/postid/{postid}")
	public String getBoardByPostid(@PathVariable("postid") int postid, Model model) {
		model = homeService.getBoardByPostid(postid, model);
		return "main";
	}

//	@RequestMapping(value="/getTime", method=RequestMethod.GET)
//	public @ResponseBody HashMap<String, String> getTime(Locale locale, Model model){
//		HashMap<String, String> map = new HashMap<String, String>();
//		
//		String formattedDate = homeService.getServerTime(locale);	
//		map.put("serverTime", formattedDate);
//		
//		return map;
//	}
}
