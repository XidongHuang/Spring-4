package com.tony.spring.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JDBCTest {

	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	{
		
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate)ctx.getBean("jdbcTemplate");
		employeeDao = ctx.getBean(EmployeeDao.class);
		departmentDao = ctx.getBean(DepartmentDao.class);
		namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
		
	}
	
	
	/**
	 * For named argument, can use update(String sql, SqlParameterSource paramSource) to operate
	 * 1. Arguments' name in SQL should be same as mapping class's fields 
	 * 
	 */
	@Test
	public void testNamedParameterJdbcTemplate2(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:lastName, :email, :deptId)";
		
		Employee employee = new Employee();
		employee.setLastName("XYZ");
		employee.setEmail("ppp@gmail.com");
		employee.setDeptId(3);
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
		
		namedParameterJdbcTemplate.update(sql, paramSource);
		
		
	}
	
	
	
	/**
	 * Can give names to parameters
	 * 1. Advantage: If there are many parameters, then just need to assign values to corresponding name, easy to maintenance
	 * 2. Disadvantage: Too much works.
	 * 
	 */
	@Test
	public void testNameParameterJdbcTemplate(){
		
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:ln, :email, :deptid)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ln", "FF");
		paramMap.put("email", "dfadf@gmail.com");
		paramMap.put("deptid", 2);
		
		namedParameterJdbcTemplate.update(sql, paramMap);
				
		
		
	}
	
	/**
	 * 
	 * NOT advice to use JdbcDaoSupport, instead of JdbcTemplate to be the field of DAO class
	 * 
	 */
	@Test
	public void testDepartmentDao(){
		System.out.println(departmentDao.get(1));
		
		
	}
	
	
	@Test
	public void testEmployeeDao(){
		
		System.out.println(employeeDao.get(1));
		
		
	}
	
	
	
	
	/**
	 * Gain single row's value, or do static query 
	 * 
	 */
	@Test
	public void testQueryForObject2(){
		
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql, Long.class);
		
		System.out.println(count);
		
		
	}
	
	
	
	/**
	 * Query instances collection
	 * Invoke "query(String sql, RowMapper<Employee> rowMapper, Object... args)" 
	 */
	@Test
	public void testQueryForList(){
		String sql = "SELECT id,last_name lastName, email FROM employees WHERE id>?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper, 5);
		
		System.out.println(employees);
	}
	
	
	
	
	
	/**
	 * Abstract a record from database, then gain an object 
	 * NOT invoke: queryForList(String sql, Class<Employee> elementType, Object... args) this method
	 * The RIGHT one is: queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
	 * 1. "RowMapper" is the mapper for mapping result, instance class is "BeanPropertyRowMapper" 
	 * 2. Using SQL rows' alias to map database's table attributes to class's fields.
	 * 3. It does not support Cascade attribute. It is just a small tool not ORM framework
	 */
	@Test
	public void testQueryForObject(){
		
		String sql = "SELECT id,last_name lastName, email FROM employees WHERE id=?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		
		System.out.println(employee);
		
		
	}
	
	
	/**
	 * Execute batch update: Batch insert, update, delete
	 * 
	 */
	@Test
	public void testBatchUpdate(){
		
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(?,?,?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		
		batchArgs.add(new Object[]{"AA", "aa@gmail.com", 1});
		batchArgs.add(new Object[]{"BB", "bb@gmail.com", 2});
		batchArgs.add(new Object[]{"CC", "cc@gmail.com", 3});
		batchArgs.add(new Object[]{"DD", "dd@gmail.com", 4});
		batchArgs.add(new Object[]{"EE", "ee@gmail.com", 5});
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
		
	}
	
	
	
	/**
	 * Execute CRUD 
	 * 
	 */
	@Test
	public void testUpdate(){
		
		String sql = "UPDATE employees SET last_name = ? WHERE id=?";
		jdbcTemplate.update(sql,"Jack", 5);
		
	}
	
	
	
	@Test
	public void testDataSource() throws SQLException{
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
		
		
	}
	
}
