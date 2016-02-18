package com.tony.spring.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDao extends JdbcDaoSupport{

	@Autowired
	public void setDataSource2(DataSource dataSource){
		
		setDataSource(dataSource);
	}
	
	public Department get(Integer id){
		String sql = "SELECT dept_id id, dept_name name FROM departments WHERE dept_id=?";
		RowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
		
		return getJdbcTemplate().queryForObject(sql, rowMapper, id);
		
		
	}	
	
	
}
