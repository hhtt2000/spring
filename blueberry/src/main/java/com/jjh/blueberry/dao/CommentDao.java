package com.jjh.blueberry.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jjh.blueberry.dto.CommentDto;

@Repository
public class CommentDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public ArrayList<CommentDto> getComment(int id) {
		String sql = "SELECT name, password, content, regdate, postid FROM comment WHERE postid = ?";
		return (ArrayList<CommentDto>) this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<CommentDto>(CommentDto.class), id);
	}
	//transactional with updateCommentNo
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
	//transactional with insertComment(코멘트 갯수)
	public int updateCommentNo(int id){
		String sql = "UPDATE board B INNER JOIN comment C ON B.id = C.postid SET B.commentno = B.commentno + 1 WHERE B.id = ?";
		return this.jdbcTemplate.update(sql, id);
	}
	
	public int getCommentNo(int id){
		String sql = "SELECT B.commentno FROM board B INNER JOIN comment C ON B.id = C.postid WHERE B.id = ? GROUP BY B.commentno";
		int commentNo = this.jdbcTemplate.queryForObject(sql, Integer.class, id);
		return commentNo;
	}
}
