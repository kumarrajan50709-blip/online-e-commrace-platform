package com.ecommerce.dao;

import com.ecommerce.db.DBConnection;
import com.ecommerce.model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class ProductDAO {

    public boolean placeOrder(List<CartItem> cart) {

        String updateStock =
                "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
        String insertOrder =
                "INSERT INTO orders(product_id, quantity) VALUES(?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try (PreparedStatement psStock = con.prepareStatement(updateStock);
                 PreparedStatement psOrder = con.prepareStatement(insertOrder)) {

                for (CartItem item : cart) {

                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getProduct().getId());
                    psStock.setInt(3, item.getQuantity());

                    if (psStock.executeUpdate() == 0) {
                        con.rollback();
                        return false;
                    }

                    psOrder.setInt(1, item.getProduct().getId());
                    psOrder.setInt(2, item.getQuantity());
                    psOrder.executeUpdate();
                }

                con.commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
