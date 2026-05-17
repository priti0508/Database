package dao;

import db.DBConnection;
import java.sql.*;

public class PaymentDAO {
    public boolean recordPayment(int userId, int subId, double amount, String method) {
        String sql = "{CALL RecordPayment(?, ?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.setInt(2, subId);
            cstmt.setDouble(3, amount);
            cstmt.setString(4, method);
            return cstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getTotalRevenue() {
        String sql = "{? = call GetTotalRevenue()}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.registerOutParameter(1, Types.DECIMAL);
            cstmt.execute();
            return cstmt.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public void printPremiumSpenders() {
        String sql = "SELECT user_id, amount FROM PAYMENTS WHERE amount > (SELECT AVG(amount) FROM PAYMENTS)";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("user_id") + " paid above average: INR " + rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}