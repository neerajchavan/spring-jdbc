package com.student.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.student.pojo.Student;

import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student newStudent = new Student();
        newStudent.setRollNo(rs.getInt("roll_no"));
        newStudent.setName(rs.getString("student_name"));
        newStudent.setAddress(rs.getString("student_address"));
        
        System.out.println("mapRow() method called...");
        
        return newStudent;
    }
    
}



