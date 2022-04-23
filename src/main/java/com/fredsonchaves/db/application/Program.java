package com.fredsonchaves.db.application;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.db.DBException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {

    public static void main(String[] args) {
        selectDepartament();
    }

    public static void selectDepartament() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Connection connection = DB.getConnection();
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
}
