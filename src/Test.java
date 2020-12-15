import com.student.dao.StudentDAOImpl;
import com.student.pojo.Student;
import com.student.service.StudentDAOHelper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentDAOHelper studentHelper =  context.getBean("studentDAOHelper", StudentDAOHelper.class);
        StudentDAOImpl studentDAO = (StudentDAOImpl) context.getBean("studentDAOImpl");

        //Fetching all the records
        //studentHelper.printStudents(studentDAO.getAllStudents());

        //Fetching all records by using BeanPropertyRowMapper
        studentHelper.printStudents(studentDAO.getAllStudentsBeanPropertyRowMapper());

        //Fetching record by roll no
        // Student student = studentDAO.findStudentByRollNo(5);
        // System.out.println("Fetched student data : " + student);
    }
}