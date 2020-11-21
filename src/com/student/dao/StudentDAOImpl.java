package com.student.dao;

import com.student.pojo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("studentDAOImpl")
public class StudentDAOImpl implements StudentDAO {

    // JdbcTemplate bean will be created using xml and @Autowired annotation will
    // inject this dependency for StudentDAOImpl class. We can also remove setter() for jdbcTemplate.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(Student student) {

        String sql = "insert into Student values(?,?,?)";

        // Using object array because our array will have all sorts of values
        // i.e.(String, Integer) etc.
        Object[] args = { student.getRollNo(), student.getName(), student.getAddress() };

        int i = jdbcTemplate.update(sql, args);

        System.out.println("No of rows inserted : " + i);
    }

}