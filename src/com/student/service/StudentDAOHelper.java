package com.student.service;

import java.util.ArrayList;
import java.util.List;
import com.student.dao.StudentDAO;
import com.student.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentDAOHelper {

    @Autowired
    private StudentDAO studentDAOImpl;

    public void setUpStudentTable(){
    Student s1 = new Student();
    s1.setRollNo(5);
    s1.setName("Chichu");
    s1.setAddress("address of chichu");
    
    Student s2 = new Student();
    s2.setRollNo(6);
    s2.setName("Vaddar");
    s2.setAddress("address of vaddar");

    Student s3 = new Student();
    s3.setRollNo(7);
    s3.setName("Nikhil");
    s3.setAddress("address of nikhil");

    List<Student> studentList = new ArrayList<Student>();
    studentList.add(s1);
    studentList.add(s2);
    studentList.add(s3);

    studentDAOImpl.insertStudents(studentList);
    
    }

}
