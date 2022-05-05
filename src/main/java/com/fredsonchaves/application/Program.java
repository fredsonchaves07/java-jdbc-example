package com.fredsonchaves.application;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.db.DBException;
import com.fredsonchaves.db.DBIntegrityException;
import com.fredsonchaves.models.dao.DaoFactory;
import com.fredsonchaves.models.dao.SellerDAO;
import com.fredsonchaves.models.entities.Department;
import com.fredsonchaves.models.entities.Seller;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {

    public static void main(String[] args) {

        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
