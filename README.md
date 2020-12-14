### This project has one four different branches:
1) **master**
2) **standard** - this branch shows the problems with the standard implementation of programs without using `XML` or `Annotations`
3) **xml** - This branch shows the working of spring using `XML` file.
4) **annotation** - This branch shows the working of spring using annotations

## [Spring JDBC - Overview](https://www.simplilearn.com/spring-jdbc-hibernate-tutorial)

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies


## Implementation of StudentDAOImpl with annotations
StudentDAOImpl code - [Click here](https://github.com/neerajchavan/spring-jdbc/blob/annotation/src/com/student/dao/StudentDAOImpl.java)

1) We are using **@Repository** annotation for `StudentDAOImpl` class.
   Read [this](https://medium.com/@sendvjs/difference-between-component-service-controller-and-repository-in-spring-5f9fa05bcb1d) article to understand difference between **@Repository, @Service & @Component** annotation.
2) We are using **@Autowired** annotation So, there is no need to write setter method for `jdbcTemplate`.
 

    ```java
    @Repository("studentDAOImpl")
    public class StudentDAOImpl implements StudentDAO {
        
        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        public void insert(Student student) {

            String sql = "insert into Student values(?,?,?)";

            // Using object array because our array will have all sorts of values
            // i.e.(String, Integer) etc.
            Object[] args = { student.getRollNo(), student.getName(), student.getAddress() };

            int i = jdbcTemplate.update(sql, args);

            System.out.println("No of rows inserted : " + i);
        }

    }
    ```

3) `JdbcTemplate` bean will be created using xml and **@Autowired** annotation will
    inject this dependency for `StudentDAOImpl` class. To enable configuration using annotations we use `<context:component-scan>` in xml file.

   ```xml
    <context:component-scan base-package="com.student"/>
        
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <constructor-arg name="url" value="jdbc:mysql://127.0.0.1:3306/spring_jdbc_school"/>
            <constructor-arg name="username" value="root"/>
            <constructor-arg name="password" value="root1234"/>
    </bean>

    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
            <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>
   ```

4) We added `deleteStudentByRollNo()` and `deleteStudentByNameAndAdress()` functionality in `StudentDAOImpl` class next. In this approach we didn't need to create connection with DB and do the repetitive tasks we usually do. i.e. (Passing  username & password of DB, executing query etc.) 
   ```Java
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
   ```

5) Our next added functionality is `insertStudnets()`. Here we are doing a batch insert of multiple student records. In `StudentDAOHelper` class we are creating three different Student objects and setting values for them. On `StudentDAOHelper` class we have used **@Service** annotation.

    ```java
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
    ```




