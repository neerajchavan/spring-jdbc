import com.student.dao.StudentDAO;
import com.student.pojo.Student;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("Xml file loaded!");
        StudentDAO sDao = (StudentDAO) context.getBean("studentDAOImpl");

        Student s1 = new Student();
        s1.setRollNo(4);
        s1.setName("Bhakti");
        s1.setAddress("address of bhakti");

        sDao.insert(s1);
    }
}