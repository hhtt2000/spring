package com.jjh.blueberry.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jjh.blueberry.controller.CommentController;
import com.jjh.blueberry.dto.CommentDto;

@Repository
public class CommentDao {

	static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CommentController.class);
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public ArrayList<CommentDto> getComment(int id) {
		String sql = "SELECT name, password, content, regdate, postid FROM comment WHERE postid = ?";
		return (ArrayList<CommentDto>) this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<CommentDto>(CommentDto.class), id);
	}

	public int insertComment(final CommentDto commentDto) {
		String sql = "INSERT INTO comment (name, password, content, regdate, postid) VALUES (?, ?, ?, now(), ?)";
		return this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, commentDto.getName());
				preparedStatement.setString(2, commentDto.getPassword());
				preparedStatement.setString(3, commentDto.getContent());
				preparedStatement.setInt(4, commentDto.getPostid());
			}
		});
	}
}
