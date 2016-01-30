package com.jjh.blueberry.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jjh.blueberry.dto.AccountDto;

@Repository
public class UserDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public int addAccount(AccountDto account) {
		String sql = "INSERT INTO users (userid, name, password, enabled) VALUES (?, ?, ?, true)";
		return this.jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, account.getUserid());
				ps.setString(2, account.getName());
				ps.setString(3, account.getPassword());
			}
		});
	}
	
	public int addRoles(String userid) {
		final String role = "ROLE_USER";
		String sql = "INSERT INTO user_roles (userid, role) VALUES (?, ?)";
		return this.jdbcTemplate.update(sql, userid, role);
	}
	
	public int findIfUserExistByGivenUserId(String userId) {
		String sql = "SELECT count(*) FROM users WHERE userid = ?";
		return this.jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}
	
	public String getUserNameById(String userId) {
		String sql = "SELECT name FROM users WHERE userid = ?";
		return this.jdbcTemplate.queryForObject(sql, String.class, userId);
	}
	
	public AccountDto getUserInfoById(String userId) {
		String sql = "SELECT name, password FROM users WHERE userid = ?";
		return this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<AccountDto>(AccountDto.class), userId);
	}

}
