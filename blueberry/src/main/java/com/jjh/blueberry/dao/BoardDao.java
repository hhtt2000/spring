package com.jjh.blueberry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
			System.out.println("in try: "+result);
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
		System.out.println("out of try: "+result);
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
				dto.setDate(rs.getTimestamp(5));
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
}
