package com.student.dao;

import java.util.ArrayList;
import java.util.List;

import com.student.pojo.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("studentDAOImpl")
public class StudentDAOImpl implements StudentDAO {

    // JdbcTemplate bean will be created using xml and @Autowired annotation will
    // inject this dependency for StudentDAOImpl class. We can also remove setter()
    // for jdbcTemplate.
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertStudent(Student student) {

        String sql = "insert into Student values(?,?,?)";

        // Using object array because our array will have all sorts of values
        // i.e.(String, Integer) etc.
        Object[] args = { student.getRollNo(), student.getName(), student.getAddress() };

        int noOfRowsInserted = jdbcTemplate.update(sql, args);

        System.out.println("No of rows inserted : " + noOfRowsInserted);
    }

    // This method inserts student data in batch
    @Override
    public void insertStudents(List<Student> studentsList) {
        String sql = "insert into Student values (?,?,?)";
        List<Object[]> sqlArgs = new ArrayList<>();

        for (Student student : studentsList) {
            Object[] studentData = { student.getRollNo(), student.getName(), student.getAddress() };
            sqlArgs.add(studentData);
        }

        jdbcTemplate.batchUpdate(sql, sqlArgs);
        System.out.println("Batch update done!");

    }

    // This method deletes student record by using student roll no
    @Override
    public boolean deleteStudentByRollNo(int rollNo) {
        String sql = "delete from Student where roll_no = ?";
        int noOfRowsDeleted = jdbcTemplate.update(sql, rollNo);
        System.out.println("No of rows deleted : " + noOfRowsDeleted);
        return noOfRowsDeleted == 1;
    }

    // This method deletes student record by using name and address
    @Override
    public int deleteStudentByNameAndAddress(String name, String address) {
        String sql = "delete from Student where student_name = ? AND student_address = ?";
        Object[] arg = { name, address };
        int noOfRowsDeleted = jdbcTemplate.update(sql, arg);
        System.out.println("No of rows deleted : " + noOfRowsDeleted);
        return noOfRowsDeleted;
    }

}