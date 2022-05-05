package com.fredsonchaves.models.dao;

import com.fredsonchaves.models.entities.Seller;

import java.util.List;

public interface SelerDAO {

    void insert(Seller seller);
    void update(Seller seller);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
