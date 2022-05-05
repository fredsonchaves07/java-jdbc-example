package com.fredsonchaves.models.dao;

import com.fredsonchaves.models.entities.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DepartmentDAO {

    void insert(Department department);
    void update(Department department);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
    default boolean resultSetIsNext(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }
}
