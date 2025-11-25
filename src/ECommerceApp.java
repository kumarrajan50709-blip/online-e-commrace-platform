import java.util.*;

public class ECommerceApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Product List
        List<Product> products = Arrays.asList(
                new Product(1, "Laptop", 55000, 10),
                new Product(2, "Smartphone", 20000, 15),
                new Product(3, "Headphones", 1500, 30),
                new Product(4, "Smartwatch", 5000, 20)
        );

        Cart cart = new Cart();
        int choice;

        while (true) {
            System.out.println("\n=== ONLINE E-COMMERCE PLATFORM ===");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Available Products ---");
                    for (Product p : products) System.out.println(p);
                    break;

                case 2:
                    System.out.print("Enter Product ID: ");
                    int id = sc.nextInt();

                    Product selected = products.stream()
                            .filter(p -> p.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (selected == null) {
                        System.out.println("Invalid product!");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();

                    if (qty > selected.getStock()) {
                        System.out.println("Not enough stock!");
                        break;
                    }

                    cart.addToCart(selected, qty);
                    System.out.println("Added to cart successfully!");
                    break;

                case 3:
                    cart.viewCart();
                    break;

                case 4:
                    double total = cart.getTotal();
                    if (total == 0) {
                        System.out.println("Cart is empty!");
                    } else {
                        System.out.println("Total Amount: ₹" + total);
                        System.out.println("Checkout Successful! Thanks for shopping!");
                        cart.clearCart();
                    }
                    break;

                case 5:
                    System.out.println("Thank you for using our platform!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}

