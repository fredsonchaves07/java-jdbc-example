package com.fredsonchaves.models.dao.impl;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.db.DBException;
import com.fredsonchaves.models.dao.SellerDAO;
import com.fredsonchaves.models.entities.Department;
import com.fredsonchaves.models.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerJDBC implements SellerDAO {

    private Connection connection;

    public SellerJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                        INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)
                        VALUES (?, ?, ?, ?, ?)
                     """,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DBException("Unexpected error! No rows affected!");
            }
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                        UPDATE seller 
                        SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?
                        WHERE Id = ?
                     """);
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            preparedStatement.setDouble(4, seller.getBaseSalary());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("""
                        DELETE FROM seller
                        WHERE Id = ?
                     """);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
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
                Department department = instantiateDepartament(resultSet);
                Seller seller = instantiateSeller(resultSet, department);
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

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartament(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    private boolean resultSetIsNext(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("""
                        SELECT seller.*, department.Name as DepName
                        FROM seller
                        INNER JOIN department on seller.DepartmentId = department.Id
                        ORDER BY Name;
                    """);
            resultSet = preparedStatement.executeQuery();
            while (resultSetIsNext(resultSet)) {
                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartament(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartament(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> departmentMap = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement("""
                        SELECT seller.*, department.Name as DepName
                        FROM seller
                        INNER JOIN department on seller.DepartmentId = department.Id
                        WHERE DepartmentId = ?
                        ORDER BY Name;
                    """);
            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSetIsNext(resultSet)) {
                Department dep = departmentMap.get(resultSet.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartament(resultSet);
                    departmentMap.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }
}
