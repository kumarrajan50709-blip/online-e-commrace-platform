package com.ecommerce.ui;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private ProductDAO dao = new ProductDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private List<CartItem> cart = new ArrayList<>();

    public MainFrame() {
        super("E-Commerce App");
        initUI();
        loadProducts();
    }

    private void initUI() {
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Products");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        top.add(title, BorderLayout.WEST);

        JButton viewCartBtn = new JButton("View Cart");
        viewCartBtn.addActionListener(e -> openCart());
        top.add(viewCartBtn, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Description", "Price", "Stock"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addToCart = new JButton("Add to Cart");
        addToCart.addActionListener(e -> addSelectedToCart());
        bottom.add(addToCart);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadProducts());
        bottom.add(refresh);

        add(bottom, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        int id = (int) tableModel.getValueAt(row, 0);
                        Product p = dao.findById(id);
                        if (p != null) {
                            JOptionPane.showMessageDialog(MainFrame.this,
                                    p.getName() + "\n\n" + p.getDescription() +
                                    "\nPrice: ₹" + p.getPrice() +
                                    "\nStock: " + p.getStock(),
                                    "Product Details",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        for (Product p : dao.getAll()) {
            tableModel.addRow(new Object[]{
                    p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getStock()
            });
        }
    }

    private void addSelectedToCart() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Product p = dao.findById(id);
        if (p == null) return;

        String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity:", "1");
        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();
            if (qty > p.getStock()) {
                JOptionPane.showMessageDialog(this, "Insufficient stock.");
                return;
            }

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProduct().getId() == p.getId()) {
                    item.setQuantity(item.getQuantity() + qty);
                    found = true;
                    break;
                }
            }
            if (!found) cart.add(new CartItem(p, qty));

            JOptionPane.showMessageDialog(this, "Added to cart.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.");
        }
    }

    private void openCart() {
        CartDialog dialog = new CartDialog(this, cart);
        dialog.setVisible(true);

        if (!cart.isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(
                    this, "Do you want to checkout now?",
                    "Checkout", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                performCheckout();
            }
        }
    }

    private void performCheckout() {
        double total = 0;
        for (CartItem item : cart) total += item.getTotal();

        boolean allOk = true;
        for (CartItem item : cart) {
            boolean ok = dao.reduceStock(
                    item.getProduct().getId(),
                    item.getQuantity());
            if (!ok) {
                allOk = false;
                JOptionPane.showMessageDialog(this,
                        "Failed to purchase: " +
                        item.getProduct().getName());
            }
        }

        if (allOk) {
            JOptionPane.showMessageDialog(this,
                    "Purchase successful! Total: ₹" + total);
            cart.clear();
            loadProducts();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Purchase failed. Review cart.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new MainFrame().setVisible(true));
    }
}