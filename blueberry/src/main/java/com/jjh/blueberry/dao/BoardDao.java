package com.jjh.blueberry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@Repository
public class BoardDao {

	DataSource dataSource;

	public BoardDao() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public int insertText(BoardDto dto){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		try{
			conn = dataSource.getConnection();
			String sql = "INSERT INTO board (userid, name, title, content, reg_date, category)VALUES (?, ?, ?, ? ,now(), ?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, dto.getUserid());
			preparedStatement.setString(2, dto.getName());
			preparedStatement.setString(3, dto.getTitle());
			preparedStatement.setString(4, dto.getContent());
			preparedStatement.setString(5, dto.getCategory());
			result = preparedStatement.executeUpdate();
	
		} catch(Exception e){
			e.printStackTrace();
		} finally {
				try {
					if(preparedStatement != null)preparedStatement.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	public BoardDto selectOne(int id) {
		BoardDto dto = new BoardDto();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try{
			conn = dataSource.getConnection();
			String sql = "SELECT userid, name, title, content, reg_date, category FROM board WHERE id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				dto.setId(id);
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getDate("reg_date"));
				dto.setCategory(rs.getString("category"));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
				try {
					if(rs != null)rs.close();
					if(preparedStatement != null)preparedStatement.close();
					if(conn != null)conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return dto;
	}

	public ArrayList<BoardDto> getList(String category, int fromRowNum, int countList) {
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT id, userid, name, title, content, reg_date, category FROM board";
		if(category != null){
			sql += " WHERE category = ?";
		}
		sql += " ORDER BY id DESC LIMIT ?, ?";
		ResultSet resultSet = null;
		
		try{
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(sql);
			if(category != null){
				preparedStatement.setString(1, category);
				preparedStatement.setInt(2, fromRowNum);
				preparedStatement.setInt(3, countList);				
			} else{
				preparedStatement.setInt(1, fromRowNum);
				preparedStatement.setInt(2, countList);
			}
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				BoardDto dto = new BoardDto();
				dto.setId(resultSet.getInt("id"));
				dto.setUserid(resultSet.getString("userid"));
				dto.setName(resultSet.getString("name"));
				dto.setTitle(resultSet.getString("title"));
				dto.setContent(resultSet.getString("content"));
				dto.setDate(resultSet.getDate("reg_date"));
				dto.setCategory(resultSet.getString("category"));
				
				list.add(dto);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null)resultSet.close();
				if(preparedStatement != null)preparedStatement.close();
				if(conn != null)conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}		
		return list;
	}

	public int updateText(BoardDto boardDto) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		try{
			conn = dataSource.getConnection();
			String sql = "UPDATE board SET title = ?, content = ?, reg_date = ?, category = ? WHERE id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, boardDto.getTitle());
			preparedStatement.setString(2, boardDto.getContent());
			preparedStatement.setDate(3, boardDto.getDate());
			preparedStatement.setString(4, boardDto.getCategory());
			preparedStatement.setInt(5, boardDto.getId());
			result = preparedStatement.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(preparedStatement != null)preparedStatement.close();	
				if(conn != null)conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	
		return result;
	}

	public int deleteText(int id) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		try{
			conn = dataSource.getConnection();
			String sql = "DELETE FROM board WHERE id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(preparedStatement != null)preparedStatement.close();
				if(conn != null)conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String getBoardUserName(String userId) {
		String name = "";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT name FROM users WHERE userid = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				name = resultSet.getString("name");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public ArrayList<CategoryDto> getCategories() {
		ArrayList<CategoryDto> categoryList = new ArrayList<CategoryDto>();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT name FROM category";
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				CategoryDto dto = new CategoryDto();
				dto.setCategoryName(resultSet.getString("name"));
//				dto.setNumCategoryList(resultSet.getInt("num"));
				
				categoryList.add(dto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null)resultSet.close();
				if(preparedStatement != null)preparedStatement.close();
				if(conn != null)conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}		
		}
		
		return categoryList;
	}

	public int insertCategory(CategoryDto categoryDto) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		int result = 0;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO category (name) VALUES (?)";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, categoryDto.getCategoryName());
			result = preparedStatement.executeUpdate();
			
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(preparedStatement != null)preparedStatement.close();
				if(conn != null)conn.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	public int getTotalCount(String category) {
		int totalCount = 0;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT count(*) AS totalCount FROM board";
			if(category != null){
				sql += " WHERE category = ?";
			}
			preparedStatement = conn.prepareStatement(sql);
			if(category != null){
				preparedStatement.setString(1, category);
			}
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				totalCount = resultSet.getInt("totalCount");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(resultSet != null)resultSet.close();
				if(preparedStatement != null)preparedStatement.close();
				if(conn != null)conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return totalCount;
	}

}




