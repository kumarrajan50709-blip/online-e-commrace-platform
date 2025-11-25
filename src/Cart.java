import java.util.*;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        items.add(new CartItem(product, quantity));
        product.reduceStock(quantity);
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        double total = 0;
        System.out.println("\n--- Your Cart ---");
        for (CartItem item : items) {
            System.out.println(item.product.getName() + " x " +
                    item.quantity + " = ₹" + item.getTotalPrice());
            total += item.getTotalPrice();
        }
        System.out.println("Total: ₹" + total);
    }

    public double getTotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void clearCart() {
        items.clear();
    }
}
