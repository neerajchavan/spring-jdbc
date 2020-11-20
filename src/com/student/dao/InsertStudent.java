package com.student.dao;

import javax.sql.DataSource;

import com.student.pojo.Student;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class InsertStudent implements StudentDAO{

    /*
    -We are using spring so, we should avoid creating objects manually.
    -If we have hundred's of implementation classes for StudentDAO(interface), we will have to create the objects of JdbcTemplate and DataSource hundred times 
     using the following approach.
    -We can use spring to avoid repeated creation of objects.
    */

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());

    @Override
    public void insert(Student student) {
        
        String sql = "insert into Student values(?,?,?)";

        //Using object array because our array will have all sorts of values i.e. (String, Integer) etc.
        Object[] args = {student.getRollNo(), student.getName(), student.getAddress()};

        int i = jdbcTemplate.update(sql, args);

        System.out.println("No of rows inserted : "+i);
    }

    public DataSource getDataSource(){

        //Setting url, username, password of database. We can inject this values using spring
        String url="jdbc:mysql://127.0.0.1:3306/spring_jdbc_school";
        String userName="root";
        String password="root1234";

        //We are using spring so, we should avoid creating objects manually
        DataSource dataSource = new DriverManagerDataSource(url, userName, password);
        return dataSource;

    }
    
}