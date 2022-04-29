package com.fredsonchaves.application;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.db.DBException;
import com.fredsonchaves.db.DBIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

    public static void main(String[] args) {
        try {
            selectDepartament();
            insertSeller();
            updateSeller();
            deleteDepartament();
        } catch (DBException error) {
            System.out.println(error.getMessage());
        }
    }

    public static void selectDepartament() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DB.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM department");
            while (resultSet.next()) System.out.println(resultSet.getString("name"));
        } catch (SQLException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
            DB.closeConnection();
        }
    }

    public static void insertSeller() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            connection = DB.getConnection();
            preparedStatement = connection.prepareStatement("""
                                    INSERT INTO seller
                                    (Name, Email, BirthDate, BaseSalary, DepartmentId)
                                    VALUES (?, ?, ?, ?, ?)""",
                                Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "Carl Purple");
            preparedStatement.setString(2, "carl@gmail.com");
            preparedStatement.setDate(3, new java.sql.Date(simpleDateFormat.parse("22/04/1985").getTime()));
            preparedStatement.setDouble(4, 3000.0);
            preparedStatement.setInt(5, 4);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Done! id = " + id);
                }
            } else {
                System.out.println("No rown affected!");
            }
        } catch (SQLException | ParseException error) {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }

    public static void updateSeller() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DB.getConnection();
            preparedStatement = connection.prepareStatement("""
                                    UPDATE seller
                                    SET BaseSalary = BaseSalary + ?
                                    WHERE Id = ?""");
            preparedStatement.setDouble(1, 200.0);
            preparedStatement.setInt(2, 2);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Done! id = " + id);
                }
            } else {
                System.out.println("No rown affected!");
            }
        } catch (SQLException error)  {
            throw new DBException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }

    public static void deleteDepartament() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DB.getConnection();
            preparedStatement = connection.prepareStatement("""
                                   DELETE FROM department
                                   WHERE Id = ?""");
            preparedStatement.setInt(1, 5);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    System.out.println("Done! id = " + id);
                }
            } else {
                System.out.println("No rown affected!");
            }
        } catch (SQLException error)  {
            throw new DBIntegrityException(error.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeConnection();
        }
    }
}
