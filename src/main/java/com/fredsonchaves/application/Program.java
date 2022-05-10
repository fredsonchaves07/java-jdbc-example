package com.fredsonchaves.application;

import com.fredsonchaves.models.dao.DaoFactory;
import com.fredsonchaves.models.dao.SellerDAO;
import com.fredsonchaves.models.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        System.out.println("=== TEST #1: seller findById ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
    }
}
