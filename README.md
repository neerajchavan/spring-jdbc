## Getting Started

This branch demonstrates the `XML` configuration for this project.

### This project has one four different branches:
1) **master**
2) **standard** - This branch shows the standard implementation of programs without using `XML` or `Annotations`
3) **xml** - This branch shows the working of spring using `XML` file.
4) **annotation** - This branch shows the working of spring using annotations

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Spring Bean Configuration

**beans.xml** 
```xml

<bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
    <constructor-arg name="url" value="jdbc:mysql://127.0.0.1:3306/spring_jdbc_school"/>
    <constructor-arg name="username" value="root"/>
    <constructor-arg name="password" value="root1234"/>
</bean>

<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <constructor-arg name="dataSource" ref="dataSource"/>
</bean>

<bean id="insertStudent" class="com.student.dao.InsertStudent">
    <property name="jdbcTemplate" ref="jdbcTemplate"/>
</bean>

```

