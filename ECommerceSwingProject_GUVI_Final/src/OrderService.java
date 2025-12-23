
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// ✅ IMPORTANT IMPORT (YE HI MISSING THA)
import com.ecommerce.db.DBConnection;

 class OrderService {

    public static void placeOrder(int productId, int quantity, int userId) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();   // ✅ ab error nahi aayega
            con.setAutoCommit(false);

            String updateInventory =
                    "UPDATE inventory SET stock = stock - ? WHERE product_id = ? AND stock >= ?";
            PreparedStatement ps1 = con.prepareStatement(updateInventory);
            ps1.setInt(1, quantity);
            ps1.setInt(2, productId);
            ps1.setInt(3, quantity);

            if (ps1.executeUpdate() == 0) {
                throw new SQLException("Insufficient stock");
            }

            String insertOrder =
                    "INSERT INTO orders(user_id, product_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(insertOrder);
            ps2.setInt(1, userId);
            ps2.setInt(2, productId);
            ps2.setInt(3, quantity);
            ps2.executeUpdate();

            con.commit(); // ✅ TRANSACTION SUCCESS
            System.out.println("Order placed successfully by User " + userId);

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // 🔁 rollback
                    System.out.println("Transaction rolled back");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
