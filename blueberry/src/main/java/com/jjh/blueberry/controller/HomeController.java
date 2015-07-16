package com.jjh.blueberry.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/todo.jh", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		locale = new Locale("ko", "KOREA");
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);
		/*mav.addObject("serverTime", formattedDate);
		mav.setViewName("home");*/
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="/getTime.jh", method=RequestMethod.POST)
	public @ResponseBody HashMap<String, String> getTime(Locale locale, Model model){
		locale = new Locale("ko", "KOREA");
		HashMap<String, String> map = new HashMap<String, String>();
		Date date = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		dateFormat.setTimeZone(tz);
		String formattedDate = dateFormat.format(date);
		
		map.put("serverTime", formattedDate);
		
		return map;
	}
	
	@RequestMapping("/main.jh")
	public String main(Model model){
		return "main";
	}
	
}
