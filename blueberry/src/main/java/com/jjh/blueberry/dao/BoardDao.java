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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jjh.blueberry.dto.BoardDto;

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
			String sql = "INSERT INTO board (userid, name, title, content, reg_date)VALUES (?, ?, ?, ? ,now())";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, "spring");
			preparedStatement.setString(2, dto.getName());
			preparedStatement.setString(3, dto.getTitle());
			preparedStatement.setString(4, dto.getContent());
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
			String sql = "SELECT userid, name, title, content, reg_date FROM board WHERE id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				dto.setId(id);
				dto.setUserid(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setDate(rs.getDate(5));
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

	public ArrayList<BoardDto> getList() {
		ArrayList<BoardDto> list = new ArrayList<BoardDto>();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT id, userid, name, title, content, reg_date FROM board ORDER BY id DESC";
		ResultSet resultSet = null;
		
		try{
			conn = dataSource.getConnection();
			preparedStatement = conn.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				BoardDto dto = new BoardDto();
				dto.setId(resultSet.getInt("id"));
				dto.setUserid(resultSet.getString("userid"));
				dto.setName(resultSet.getString("name"));
				dto.setTitle(resultSet.getString("title"));
				dto.setContent(resultSet.getString("content"));
				dto.setDate(resultSet.getDate("reg_date"));
				
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
			String sql = "UPDATE board SET title = ?, content = ?, reg_date = ? WHERE id = ?";
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, boardDto.getTitle());
			preparedStatement.setString(2, boardDto.getContent());
			preparedStatement.setDate(3, boardDto.getDate());
			preparedStatement.setInt(4, boardDto.getId());
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
}




