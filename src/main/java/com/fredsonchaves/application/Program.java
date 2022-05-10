package com.fredsonchaves.application;

import com.fredsonchaves.models.dao.DaoFactory;
import com.fredsonchaves.models.dao.SellerDAO;
import com.fredsonchaves.models.entities.Department;
import com.fredsonchaves.models.entities.Seller;

import java.util.List;

public class Program {

    public static void main(String[] args) {

        SellerDAO sellerDAO = DaoFactory.createSellerDao();
        System.out.println("=== TEST #1: seller findById ===");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller);
        System.out.println("\n=== TEST #2: seller findByDepartament ===");
        Department department = new Department(2, null);
        List<Seller> sellers = sellerDAO.findByDepartament(department);
        System.out.println(sellers);
        System.out.println("\n=== TEST #3: seller findAll ===");
        sellers = sellerDAO.findAll();
        System.out.println(sellers);
    }
}
