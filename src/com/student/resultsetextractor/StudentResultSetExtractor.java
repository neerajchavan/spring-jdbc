package com.student.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.student.pojo.Student;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StudentResultSetExtractor implements ResultSetExtractor<List<Student>> {

    // This method is called only once, it returns all the entries present in the DB and return List of data
    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Student> studentList = new ArrayList<Student>();
        
        while(rs.next()){
        Student student = new Student();
        student.setRollNo(rs.getInt("roll_no"));
        student.setName(rs.getString("student_name"));
        student.setAddress(rs.getString("student_address"));

        studentList.add(student);
        }
        
        System.out.println("Inside extractData() of ResultSetExtractor...");

        return studentList;
    }
    
}
