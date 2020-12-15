import java.util.List;
import java.util.Map;

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

        //Fetching all the records : RowMapper example
        //studentHelper.printStudents(studentDAO.getAllStudents());

        //Fetching all the records : BeanPropertyRowMapper example
        studentHelper.printStudents(studentDAO.getAllStudentsBeanPropertyRowMapper());

        //Fetching record by roll no : RowMapper example
        // Student student = studentDAO.findStudentByRollNo(5);
        // System.out.println("Fetched student data : " + student);

        // Find students by name : ResultSetExtractor example
        //studentHelper.printStudents(studentDAO.findStudentsByName("Neeraj"));
        
        //checking map assignmnet
        Map<String, List<String>> nameAdressMap = studentDAO.studentNameAddressMapping();
        System.out.println("Mapping : " + nameAdressMap);
    }
}