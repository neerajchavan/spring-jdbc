package com.student.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.student.pojo.Student;
import com.student.resultsetextractor.StudentNameAddressMapper;
import com.student.resultsetextractor.StudentResultSetExtractor;
import com.student.rowmapper.StudentRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    // This method returns all the students from DB
    @Override
    public List<Student> getAllStudents() {
        String sql = "select * from Student";
        List<Student> studentList = jdbcTemplate.query(sql, new StudentRowMapper());
        return studentList;
    }

    // This method finds students by their roll number
    @Override
    public Student findStudentByRollNo(int rollNumber) {
        String sql = "select * from Student where roll_no=?";
        Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), rollNumber);
        return student;
    }

    @Override
    public List<Student> getAllStudentsBeanPropertyRowMapper() {
        System.out.println("Inside BeanPropertyRowMapper : ");
        String sql = "select roll_no as rollNo, student_name as name, student_address as address FROM Student";

        List<Student> studentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
        return studentList;
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        String sql = "select * from Student where student_name = ?";
        List<Student> studentList = jdbcTemplate.query(sql, new StudentResultSetExtractor(), name);
        return studentList;
    }

    @Override
    public Map<String, List<String>> studentNameAddressMapping() {
        String sql = "select * from Student";
        Map<String, List<String>> map = jdbcTemplate.query(sql, new StudentNameAddressMapper());
        return map;
    }

    @Override
    public int updateStudent(int rollNo, String address) {
        String sql = "update Student set student_address = ? where roll_no = ?";
        Object[] args = { address, rollNo };
        int i = jdbcTemplate.update(sql, args);

        System.out.println("No of records updated : " + i);
        return i;
    }

    @Override
    public int batchUpdateStudents(List<Student> studentsList) {
        String sql = "update Student set student_address = ? where roll_no = ?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            // In this method we set args for PreparedStatement
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setString(1, studentsList.get(index).getAddress());
                ps.setInt(2, studentsList.get(index).getRollNo());
			}

            // In this method we define how many times the query will get executed...
            // i.e How many times setValues() will get called...
			@Override
			public int getBatchSize() {
				System.out.println("inside getBatchSize().." + " setValues() will run " +studentsList.size() + " times.");
				return studentsList.size();
			}
            
        });
        return studentsList.size();
    }

    

}