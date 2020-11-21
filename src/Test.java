import com.student.dao.StudentDAO;
import com.student.pojo.Student;
import com.student.service.StudentDAOHelper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("Xml file loaded!");
        //StudentDAO sDao = (StudentDAO) context.getBean("studentDAOImpl");
        StudentDAOHelper sHelper = (StudentDAOHelper) context.getBean("studentDAOHelper");

        // Student s1 = new Student();
        // s1.setRollNo(4);
        // s1.setName("Bhakti");
        // s1.setAddress("address of bhakti");

        //sDao.insertStudent(s1);
        //sDao.deleteStudentByRollNo(4);
       // sDao.deleteStudentByNameAndAddress("Yash", "address of yash");
       sHelper.setUpStudentTable();
    }
}