package com.jjh.blueberry.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger log = LoggerFactory.getLogger(BbsService.class);
	
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

	public Map<String, String> getUrlInfo(String url) throws IOException {
		Map<String, String> map = new HashMap<>();
		if(url.contains("&nbsp;")){
			int lastIndexOfSpace = url.lastIndexOf("&nbsp;");
			url = url.substring(0, lastIndexOfSpace);
		}
		map.put("url", url);
		log.debug("URL is {}", url);
//		Connection conn = Jsoup.connect(url).userAgent("Mozilla/5.0").ignoreHttpErrors(true).timeout(100000);
//		Response response = conn.execute();
	
		Document doc = Jsoup.parse(new URL(url).openStream(), null, url);
		
		
		String title = "";
		Elements parsedTitle = doc.select("meta[property=\"og:title\"]");
		if(!parsedTitle.isEmpty()){
			title = parsedTitle.attr("content");
		} else{
			title = doc.title();
		}
		map.put("title", title);
		
		//  <meta content="/images/branding/googleg/1x/googleg_standard_color_128dp.png" itemprop="image">
		Elements parsedImageByProperty = doc.select("meta[property=\"og:image\"]");
		StringBuilder sb = new StringBuilder();
		String image = "";
		if(!parsedImageByProperty.isEmpty()){
			image = parsedImageByProperty.attr("content");
		}
		if(image.isEmpty()){
			image = "/blueberry/resources/img/grape.png";
		}
		sb.append("<img src='");
		sb.append(image);
		sb.append("' align='left' width='100px' height='100px'>");
		map.put("image", sb.toString());			
		
		Elements parsedDescriptionByProperty = doc.select("meta[property=\"og:description\"]");
		String description = "";
		if(!parsedDescriptionByProperty.isEmpty()){
			description = parsedDescriptionByProperty.attr("content");
		} else{
			Elements parsedDescriptionByName = doc.select("meta[name=\"description\"]");
			if(!parsedDescriptionByName.isEmpty()){
				description = parsedDescriptionByName.attr("content");
			}
		}
		if(description.length() > 150){
			description = description.substring(0, 150);
		}
		map.put("description", description);			
		
		return map;
	}
}
