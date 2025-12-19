package com.ecommerce.ui;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.thread.OrderTask;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private List<CartItem> cart = new ArrayList<>();

    public MainFrame() {
        setTitle("E-Commerce Platform");
        setSize(600,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton checkout = new JButton("Checkout (Multithreaded)");
        checkout.addActionListener(e -> performCheckout());
        add(checkout, BorderLayout.CENTER);
    }

    private void performCheckout() {
        ProductDAO dao = new ProductDAO();

        Thread t1 = new Thread(new OrderTask(dao, cart, "User-1"));
        Thread t2 = new Thread(new OrderTask(dao, cart, "User-2"));

        t1.start();
        t2.start();

        Object console;
        JOptionPane.showMessageDialog(
                this,
                "Orders are processing in parallel.\nCheck console output.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
        );

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}