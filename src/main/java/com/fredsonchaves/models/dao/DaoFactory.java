package com.fredsonchaves.models.dao;

import com.fredsonchaves.db.DB;
import com.fredsonchaves.models.dao.impl.SellerJDBC;

public class DaoFactory {

    public static SellerDAO createSellerDao() {
        return new SellerJDBC(DB.getConnection());
    }
}
