
public class OrderTask implements Runnable {

    private int productId;
    private int quantity;
    private int userId;

    public OrderTask(int productId, int quantity, int userId) {
        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }

    @Override
    public void run() {
        OrderService.placeOrder(productId, quantity, userId);
    }
}
