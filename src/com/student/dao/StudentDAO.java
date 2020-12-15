package com.student.dao;

import java.util.List;
import java.util.Map;

import com.student.pojo.Student;

public interface StudentDAO {
    void insertStudent(Student student);

    void insertStudents(List<Student> studentsList);

    boolean deleteStudentByRollNo(int rollNo);

    int deleteStudentByNameAndAddress(String name, String address);

    List<Student> getAllStudents();

    Student findStudentByRollNo(int rollNumber);

    List<Student> getAllStudentsBeanPropertyRowMapper();

    List<Student> findStudentsByName(String name);

    Map<String, List<String> > studentNameAddressMapping();
}