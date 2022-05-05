package com.fredsonchaves.models.dao;

import com.fredsonchaves.models.entities.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SellerDAO {

    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    default boolean resultSetIsNext(ResultSet resultSet) throws SQLException {
        return resultSet.next();
    }
}
