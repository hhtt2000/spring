package com.jjh.blueberry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jjh.blueberry.dto.BoardDto;
import com.jjh.blueberry.dto.CategoryDto;

@Repository
public class BoardDao {
	
	Logger log = LoggerFactory.getLogger(BoardDao.class);
	
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int insertText(final BoardDto dto){
		return this.jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String sql = "INSERT INTO board (userid, name, title, content, regdate, category, commentno)VALUES (?, ?, ?, ? ,now(), ?, 0)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, dto.getUserid());
				preparedStatement.setString(2, dto.getName());
				preparedStatement.setString(3, dto.getTitle());
				preparedStatement.setString(4, dto.getContent());
				preparedStatement.setString(5, dto.getCategory());
				return preparedStatement;
			}
		});
	}

	public BoardDto selectOne(int id) {
		String sql = "SELECT id, userid, name, title, content, regdate, category, commentno FROM board WHERE id = ?";
		return this.jdbcTemplate.queryForObject(sql, new Object[] {id},
				new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
		
	}

	public ArrayList<BoardDto> getList(final String category, final int fromRowNum, final int countList) {
		String sql = "SELECT id, userid, name, title, content, regdate, category, commentno FROM board";
		if(category != null){
			sql += " WHERE category = ?";
		}
		sql += " ORDER BY id DESC LIMIT ?, ?";
		
		if(category != null){
			return (ArrayList<BoardDto>) this.jdbcTemplate.query(sql, new PreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement preparedStatement) throws SQLException {
					preparedStatement.setString(1, category);
					preparedStatement.setInt(2, fromRowNum);
					preparedStatement.setInt(3, countList);	
				}
			}, new BeanPropertyRowMapper<BoardDto>(BoardDto.class));
		} else{
			return (ArrayList<BoardDto>) this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BoardDto>(BoardDto.class), fromRowNum, countList);
		}
	}

	public int updateText(final BoardDto boardDto) {
		String sql = "UPDATE board SET title = ?, content = ?, regdate = ?, category = ? WHERE id = ?";
		int result = this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, boardDto.getTitle());
				preparedStatement.setString(2, boardDto.getContent());
				preparedStatement.setDate(3, boardDto.getRegdate());
				preparedStatement.setString(4, boardDto.getCategory());
				preparedStatement.setInt(5, boardDto.getId());
			}
		});
		return result;
	}

	public int deleteText(int id) throws DataIntegrityViolationException {		
		String sql = "DELETE FROM board WHERE id = ?";
		int result = 0;
		this.jdbcTemplate.update(sql, id);
		return result;
	}

	public ArrayList<CategoryDto> getCategories() {		
		String sql = "SELECT name FROM category";
		return (ArrayList<CategoryDto>) this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<CategoryDto>(CategoryDto.class));
	}

	public int insertCategory(final CategoryDto categoryDto) {	
		String sql = "INSERT INTO category (name) VALUES (?)";
		return this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, categoryDto.getName());
			}
		});
	}
	
	public int getTotalCount() {
		String sql = "SELECT count(*) AS totalCount FROM board";
		return this.jdbcTemplate.queryForObject(sql, Integer.class);			
	}
	
	public int getTotalCount(String category) {
		String sql = "SELECT count(*) AS totalCount FROM board";
		if(category != null){
			sql += " WHERE category = ?";
			return this.jdbcTemplate.queryForObject(sql, Integer.class, category);
		} else{
			return this.jdbcTemplate.queryForObject(sql, Integer.class);			
		}
	}

	public int getSearchListCount(String searchText) {
		String sql = "SELECT count(*) AS countSearchList FROM board WHERE title LIKE ? OR content LIKE ?";
		return this.jdbcTemplate.queryForObject(sql, new String[]{"%"+searchText+"%", "%"+searchText+"%"}, Integer.class);
	}
	
	public ArrayList<BoardDto> getSearchList(String searchText, int fromRowNum, int countList) {
		String sql = "SELECT id, userid, name, title, content, regdate, category, commentno FROM board WHERE title LIKE ? OR content LIKE ? ORDER BY id DESC LIMIT ?, ?";
		return (ArrayList<BoardDto>) this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BoardDto>(BoardDto.class), "%"+searchText+"%", "%"+searchText+"%", fromRowNum, countList);
	}

}




