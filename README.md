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
This object array must have one entry for each placeholder in the SQL statement, and they must be in the same order as they are defined in the SQL statement. We will see more examples of batch operations later.

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
6) Updating student address by roll no :
   
    ```Java
    @Override
    public int updateStudent(int rollNo, String address) {
        String sql = "update Student set student_address = ? where roll_no = ?";
        Object[] args = { address, rollNo };
        int i = jdbcTemplate.update(sql, args);

        System.out.println("No of records updated : " + i);
        return i;
    }
    ```

 # RowMapper Interface

 `RowMapper` interface allows to map a row of the relations with the instance of user-defined class. It iterates the ResultSet internally and adds it into the collection.

  ## Method of RowMapper interface
  It defines only one method mapRow that accepts ResultSet instance and int as the parameter list. Syntax of the method is given below:

  ```Java
  public T mapRow(ResultSet rs, int rowNumber)throws SQLException  
  ```

  **Example 1 : Fetching all the records using query()**
  ```JAVA
   @Override
    public List<Student> getAllStudents() {
        String sql = "select * from Student";
        List<Student> studentList = jdbcTemplate.query(sql, new StudentRowMapper());
        return studentList;
    }
  ```

  **Example 2 : Fetching a unique record using queryForObject()**
  ```JAVA
  @Override
    public Student findStudentByRollNo(int rollNumber) {
        String sql = "select * from Student where roll_no=?";
        Student student = jdbcTemplate.queryForObject(sql, new StudentRowMapper(), rollNumber);
        return student;
    }
  ```

  **Implementation of `StudentRowMapper`**
  ```JAVA
  public class StudentRowMapper implements RowMapper<Student> {

    //This function will be called for each entry in the DB and returns one record at a time
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student newStudent = new Student();
        newStudent.setRollNo(rs.getInt("roll_no"));
        newStudent.setName(rs.getString("student_name"));
        newStudent.setAddress(rs.getString("student_address"));
        
        System.out.println("Inside mapRow() of RowMapper...");
        
        return newStudent;
    }
    
}
  ```
   **Further explaination with example - [click here](https://www.tutorialspoint.com/springjdbc/springjdbc_rowmapper.htm)**

# BeanPropertyRowMapper

 - [Tutorial](https://www.youtube.com/watch?v=jMIsLd6lPDU&list=PL3NrzZBjk6m-rYTKze-5Y5RvU8eykal4j&index=4&t=2649s)

- [Documentation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/BeanPropertyRowMapper.html)

The following changes needs to be made in `beans.xml`. In `dataSource` Bean change the value for `url` -

```XML
<constructor-arg name="url" value="jdbc:mysql://127.0.0.1:3306/spring_jdbc_school?allowPublicKeyRetrieval=true&amp;useSSL=FALSE"/>
```

To use `BeanPropertyRowMapper`, we must set column names of the table same as property names of the Class.

**Example**
```Java
@Override
    public List<Student> getAllStudentsBeanPropertyRowMapper() {
        String sql = "select roll_no as rollNo, student_name as name, student_address as address FROM Student";

        List<Student> studentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
        return studentList;
    }
```

# ResultSetExtractor Interface

The `ResultSetExtractor` interface can be used to fetch records from the database. It accepts a `ResultSet` as a method argument and returns the `List`. Implementation of this interface perform the actual work of extracting results from a `ResultSet`, but you don’t need to worry about exception handling.

SQL Exceptions will be caught and handled by the calling `JdbcTemplate`. This interface is mainly used within the JDBC framework itself. 


## Method of ResultSetExtractor interface
  It defines only one method extractData that accepts ResultSet instance as a parameter. Syntax of the method is given below:

  ```Java
  public T extractData(ResultSet rs)throws SQLException,DataAccessException  
  ```

**Example : Fetching records by name using query()**

```Java
@Override
    public List<Student> findStudentsByName(String name) {
        String sql = "select * from Student where student_name = ?";
        List<Student> studentList = jdbcTemplate.query(sql, new StudentResultSetExtractor(), name);
        return studentList;
    }
```
**Implementation of `StudentResultSetExtractor`**
```Java
public class StudentResultSetExtractor implements ResultSetExtractor<List<Student>> {

    // This method is called only once, it returns all the entries present in the DB and return List of data
    @Override
    public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Student> studentList = new ArrayList<Student>();
        
        while(rs.next()){
        Student student = new Student();
        student.setRollNo(rs.getInt("roll_no"));
        student.setName(rs.getString("student_name"));
        student.setAddress(rs.getString("student_address"));

        studentList.add(student);
        }
        
        System.out.println("Inside extractData() of ResultSetExtractor...");

        return studentList;
    }
    
}
```

**Further explaination with example - [click here](https://www.tutorialspoint.com/springjdbc/springjdbc_resultsetextractor.htm)**

# Batch Operations

  `BatchPreparedStatementSetter` - [click here](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/BatchPreparedStatementSetter.html) 

  1. **Batch Update**
  ```Java
  @Override
    public int batchUpdateStudents(List<Student> studentsList) {
        String sql = "update Student set student_address = ? where roll_no = ?";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            // In this method we set args for PreparedStatement
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
                ps.setString(1, studentsList.get(index).getAddress());
                ps.setInt(2, studentsList.get(index).getRollNo());
			}

            /* In this method we define how many times the query will get executed...
            i.e How many times setValues() will get called...
            This method is executed first.*/
			@Override
			public int getBatchSize() {
				System.out.println("inside getBatchSize().." + " setValues() will run " +studentsList.size() + " times.");
				return studentsList.size();
			}
            
        });
        return studentsList.size();
    }
  ```

  **Spring documentation on batch operations** - 
[click here](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc-batch-classic)

**More examples of batch operations** - [click here](https://mkyong.com/spring/spring-jdbctemplate-batchupdate-example/)

# @Transactional

We will see more about **@Transactional** in Spring AOP. Checkout the example 4 of [this](https://mkyong.com/spring/spring-jdbctemplate-batchupdate-example/) tutorial to get the basic idea.
Before using **@Transactional** it needs to be enabled.











