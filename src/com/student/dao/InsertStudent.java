package com.student.dao;

import com.student.pojo.Student;

import org.springframework.jdbc.core.JdbcTemplate;

public class InsertStudent implements StudentDAO{

    private JdbcTemplate jdbcTemplate;

     //Spring will use this setter method to inject dependency
     public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Student student) {
        
        String sql = "insert into Student values(?,?,?)";

        //Using object array because our array will have all sorts of values i.e. (String, Integer) etc.
        Object[] args = {student.getRollNo(), student.getName(), student.getAddress()};

        int i = jdbcTemplate.update(sql, args);

        System.out.println("No of rows inserted : "+i);
    }
    
}