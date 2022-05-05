package com.fredsonchaves.models.dao.impl;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.db.DBException;
import com.fredsonchaves.models.dao.SellerDAO;
import com.fredsonchaves.models.entities.Department;
import com.fredsonchaves.models.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerJDBC implements SellerDAO {

    private Connection connection;

    public SellerJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("""
                        SELECT seller.*, department.Name as DepName
                        FROM seller
                        INNER JOIN department
                        ON department.Id = seller.DepartmentId
                        WHERE seller.ID = ?
                    """);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSetIsNext(resultSet)) {
                Department department = new Department();
                department.setId(resultSet.getInt("DepartmentId"));
                department.setName(resultSet.getString("DepName"));
                Seller seller = new Seller();
                seller.setId(resultSet.getInt("Id"));
                seller.setName(resultSet.getString("Name"));
                seller.setEmail(resultSet.getString("Email"));
                seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
                seller.setBirthDate(resultSet.getDate("BirthDate"));
                seller.setDepartment(department);
                return seller;
            }

        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }
}
