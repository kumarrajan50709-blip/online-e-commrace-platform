package com.ecommerce.thread;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.CartItem;

import java.util.List;

public class OrderTask implements Runnable {

    private ProductDAO dao;
    private List<CartItem> cart;
    private String user;

    public OrderTask(ProductDAO dao, List<CartItem> cart, String user) {
        this.dao = dao;
        this.cart = cart;
        this.user = user;
    }

    @Override
    public void run() {
        boolean result = dao.placeOrder(cart);
        System.out.println(user + " -> " + (result ? "Order Success" : "Order Failed"));
    }
}
