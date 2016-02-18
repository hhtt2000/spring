package com.jjh.blueberry.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.jjh.blueberry.dao.BoardDao;
import com.jjh.blueberry.dao.UserDao;
import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@Service
public class BbsService {

	Logger log = LoggerFactory.getLogger(BbsService.class);
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private UserDao userDao;

	public Model getInfo4NewText(Model model) {
		//session id값을 얻는 과정
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String userId = userDetail.getUsername();
		
		String name = userDao.getUserNameById(userId);
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

	public int deleteText(int id) throws DataIntegrityViolationException {
		return boardDao.deleteText(id);
	}

	public Map<String, String> getUrlInfo(String url) throws IOException {
		Map<String, String> map = new HashMap<>();
		if(url.contains("&nbsp;")){
			int lastIndexOfSpace = url.lastIndexOf("&nbsp;");
			url = url.substring(0, lastIndexOfSpace);
		}
		map.put("url", url);
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
			if(!(image.contains("http://") || image.contains("https://"))) {
				int indexOf = 0;
				if(url.contains("http://")) {
					indexOf = url.indexOf("/", 7);
				}else if(url.contains("https://")) {
					indexOf = url.indexOf("/", 8);
				}
				image = url.substring(0, indexOf).concat(image);
			}
		}
		if(image.isEmpty()){
			image = "/blueberry/resources/img/grape.png";
		}
		sb.append("<img src='");
		sb.append(image);
		sb.append("' width='100%' height=auto>");
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

	public String saveImage(MultipartFile file) throws IOException {
		//이미지 해당 경로에 저장
		Timestamp now = new Timestamp(new Date().getTime());
		long timeToLong = now.getTime();
		String originalFileName = file.getOriginalFilename();
		String fileName = timeToLong+originalFileName;
		//for linux
		String savePath = "/home/dev/local/apache-tomcat-7.0.63/webapps/blueberry/resources/img/"+fileName;
		//for local in windows
//		String savePath = "C:\\Users\\Administrator\\git\\spring\\blueberry\\src\\main\\webapp\\resources\\img\\"+fileName;
		
		String connectPath = "/blueberry/resources/img/"+fileName;
		InputStream is = file.getInputStream();
		FileOutputStream os = new FileOutputStream(savePath);
		
		int count = -1;
		byte[] buffer = new byte[2048];
		while((count = is.read(buffer)) != -1) {
			os.write(buffer, 0, count);
		}
		os.close();
		is.close();
		//충분한 여유 시간이 있으면 404 에러 나지 않음
		//for linux
		Connection urlCon = Jsoup.connect("http://127.0.0.1:8080"+connectPath);
		//for windows
//		Connection urlCon = Jsoup.connect("http://127.0.0.1:8181"+connectPath);
		
		Response response = urlCon.ignoreHttpErrors(true).ignoreContentType(true).execute();
		int status = response.statusCode();
		while(status == 404) {
			response = urlCon.ignoreHttpErrors(true).ignoreContentType(true).execute();
			status = response.statusCode();
		}
		return connectPath;
	}
}
