## Getting Started

This branch demonstrates the configuration for this project without using `XML` or `@Annotations`.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies


## Problems with this implementation
`InsertStudent` code - [Click here](https://github.com/neerajchavan/spring-jdbc/blob/standard/src/com/student/dao/InsertStudent.java)

1) We are creating `JdbcTemplate` object with `new` keyword
    ```Java
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    ```

2) In `getDataSource()` we are also creating object for `DataSource` with `new` keyword and also setting variable values ourselfs.
    ```Java
    public DataSource getDataSource(){

        String url="jdbc:mysql://127.0.0.1:3306/spring_jdbc_school";
        String userName="root";
        String password="root1234";

        DataSource dataSource = new DriverManagerDataSource(url, userName, password);
        return dataSource;
    }
    ```


## Why we shouldn't create JdbcTemplate manually?
- The `InsertStudent` class has an insert method which helps us to insert a student object to our student table.
    ```java 
    insert(Student student)
    ``` 

- Imagine, We have **multiple DAO**'s like our `InsertStudent`. For example `BookDAO`, `TeacherDAO` etc. Now imagine, we need to insert a Book object record to the book table or Teacher object to teacher table.

- So, In this scenario, we need the `JdbcTemplate` object again, inside our `BookDAO` or `TeacherDAO` so that we can call the `update()` over it, which will perform operations for us.

- So, we may need the `JdbcTemplate` object in multiple DAO classes and **we don't want to create a new JdbcTemplate object in every DAO class.**


**We shouldn't be creating objects and setting dependencies. Spring will do these things for us.**



