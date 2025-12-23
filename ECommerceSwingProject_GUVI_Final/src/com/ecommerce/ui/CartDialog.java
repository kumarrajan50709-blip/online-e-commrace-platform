package com.ecommerce.ui;

import com.ecommerce.model.CartItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartDialog extends JDialog {
    private List<CartItem> cart;

    public CartDialog(Frame owner, List<CartItem> cart) {
        super(owner, "Cart", true);
        this.cart = cart;
        initUI();
    }

    private void initUI() {
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        JPanel panel = new JPanel(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        double total = 0;
        for (CartItem item : cart) {
            model.addElement(item.toString());
            total += item.getTotal();
        }

        JList<String> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(new JLabel("Total: ₹" + total));
        JButton checkout = new JButton("Checkout");
        checkout.addActionListener(e -> dispose());
        bottom.add(checkout);

        panel.add(bottom, BorderLayout.SOUTH);
        add(panel);
    }
}