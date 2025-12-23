package com.ecommerce.dao;

import com.ecommerce.db.DBConnection;
import com.ecommerce.model.Product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id=?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean reduceStock(int id, int qty) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id=? AND stock >= ?";

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, id);
            ps.setInt(3, qty);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}