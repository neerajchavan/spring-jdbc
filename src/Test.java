import com.student.dao.StudentDAOImpl;
import com.student.service.StudentDAOHelper;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        StudentDAOHelper studentHelper =  context.getBean("studentDAOHelper", StudentDAOHelper.class);
        StudentDAOImpl studentDAO = (StudentDAOImpl) context.getBean("studentDAOImpl");
        studentHelper.printStudents(studentDAO.getAllStudents());
    }
}