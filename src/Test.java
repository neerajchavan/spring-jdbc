import com.student.dao.InsertStudent;
import com.student.dao.StudentDAO;
import com.student.pojo.Student;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("Xml file loaded!");
        StudentDAO sDao = (StudentDAO) context.getBean("insertStudent");

        Student s1 = new Student();
        s1.setRollNo(2);
        s1.setName("Yash"); 
        s1.setAddress("address of yash");

        sDao.insert(s1);
    }
}
