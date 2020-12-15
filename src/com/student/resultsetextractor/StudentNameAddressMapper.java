package com.student.resultsetextractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StudentNameAddressMapper implements ResultSetExtractor<Map<String, List<String>>> {

    @Override
    public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {

        Map<String, List<String>> studentMap = new HashMap<>();

        while (rs.next()) {
            String studentAddress = rs.getString("student_address");
            String studentName = rs.getString("student_name");

            List<String> studentsNameList = studentMap.get(studentAddress);

            if (studentsNameList == null) {
                List<String> newStudentList = new ArrayList<>();
                newStudentList.add(studentName);
                studentMap.put(studentAddress, newStudentList);
            }
            else
            {
                studentsNameList.add(studentName);
            }
        }

        return studentMap;
    }

}
