import com.student.dao.InsertStudent;
import com.student.dao.StudentDAO;
import com.student.pojo.Student;

public class Test {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.setRollNo(1);
        s1.setName("Neeraj"); 
        s1.setAddress("address of neeraj");

        //Trying to insert data into database
        StudentDAO sDao = new InsertStudent();
        sDao.insert(s1);
    }
}
