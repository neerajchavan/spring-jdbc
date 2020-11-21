## Getting Started

This branch demonstrates the `@Annotation` configuration for this project.

### This project has one four different branches:
1) **master**
2) **standard** - this branch shows the problems with the standard implementation of programs without using `XML` or `Annotations`
3) **xml** - This branch shows the working of spring using `XML` file.
4) **annotation** - This branch shows the working of spring using annotations

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies


## Implementation of StudentDAOImpl with annotations

1) We are using **@Repository** annotation for `StudentDAOImpl` class.
   Read [this](https://medium.com/@sendvjs/difference-between-component-service-controller-and-repository-in-spring-5f9fa05bcb1d) article to understand difference between **@Repository** & **@Component** annotation.
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
    inject this dependency for StudentDAOImpl class. We can also remove setter() for jdbcTemplate. To enable configuration using annotations we use `<context:component-scan>` in xml file.

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




