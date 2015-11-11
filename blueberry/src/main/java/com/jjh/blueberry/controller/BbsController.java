package com.jjh.blueberry.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jjh.blueberry.common.validator.BoardValidator;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;
import com.jjh.blueberry.service.BbsService;
import com.jjh.blueberry.service.HomeService;

@RequestMapping("/board")
@Controller
public class BbsController {
	
	Logger log = LoggerFactory.getLogger(BbsController.class);
	
	@Autowired
	private BbsService bbsService;
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping("/newText")
	public String newText(Model model){
		model = bbsService.getInfo4NewText(model);
		
		return "newText";
	}
	
	@RequestMapping(value="/newText", method=RequestMethod.POST)
	public String newTextProcess(Model model, BoardDto boardDto,
			BindingResult bindingResult) {	
		model.addAttribute("boardDto", boardDto);
		
		ArrayList<CategoryDto> categories = homeService.getCategoryList();
		model.addAttribute("categories", categories);
		
		new BoardValidator().validate(boardDto, bindingResult);
		if(bindingResult.hasErrors()){
			return "newText";
		} else {	
			int result = bbsService.insertText(boardDto);
			
			if(result == 1){
				return "redirect:/main";			
			} else {
				return "newText";			
			}
		}
	}
	
	@RequestMapping("/updateText/id/{id}")
	public String updateText(@PathVariable("id") int id, Model model){
		BoardDto dto = bbsService.getInfo4UpdateText(id);
		model.addAttribute("boardDto", dto);
		
		ArrayList<CategoryDto> categories = homeService.getCategoryList();
		model.addAttribute("categories", categories);
		
		return "updateText";
	}
	
	@RequestMapping(value="/updateText", method=RequestMethod.POST)
	public String updateProcess(@ModelAttribute BoardDto boardDto, BindingResult bindingResult) {
		new BoardValidator().validate(boardDto, bindingResult);
		int id = boardDto.getId();
		if(bindingResult.hasErrors()){
			return "forward:updateText/id/"+id;
		}
		
		int result = bbsService.updateText(boardDto);
		if(result == 1) {
			return "redirect:/main";
		} else {
			return "forward:updateText/id/"+id;			
		}
	}
	
	@RequestMapping("/deleteText/id/{id}")
	public String deleteText(@PathVariable("id") int id){
		int result = bbsService.deleteText(id);
		
		return "redirect:/main";
	}
	
	//1. @requestparam 사용(summernote.jsp ajax)
	@RequestMapping(value="/getUrlInfo")
	@ResponseBody
	public Map<String, Map<String, String>> getUrlInfo(@RequestParam("url") String url) throws IOException {
		//url = "http://www.daum.net"
		Map<String, Map<String, String>> map = new HashMap<>();
		Map<String, String> info = bbsService.getUrlInfo(url);
		map.put("info", info);
		return map;
	}
	
	//2. @requestbody 사용(summernote.jsp ajax)
//	@RequestMapping(value="/getUrlInfo", method=RequestMethod.POST)
//	@ResponseBody
//	public Map<String, String> getUrlInfo(@RequestBody String url) {
//		//url = {"url":"http://www.daum.net"}
//		Map<String, String> map = new HashMap<>();
//		String info = bbsService.getUrlInfo(url);
//		map.put("info", info);
//		return map;
//	}
	
	@RequestMapping(value="/saveImage", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	@ResponseBody
	public Map<String, String> saveImage(@RequestBody MultipartFile file) throws IOException {
		String filePath = bbsService.saveImage(file);
		Map<String, String> map = new HashMap<>();
		map.put("path", filePath);
		return map;
	}
	
}
