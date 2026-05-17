package dao;

import db.DBConnection;
import java.sql.*;

public class SubscriptionDAO {
    public boolean addSubscription(int userId, int planId, String startDate, String endDate) {
        String sql = "{CALL AddSubscription(?, ?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.setInt(2, planId);
            cstmt.setString(3, startDate);
            cstmt.setString(4, endDate);
            return cstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printUserSubscriptionDetails() {
        String sql = "SELECT * FROM UserSubscriptionDetailsView"; 
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("User: " + rs.getString("full_name") + " | Plan ID: " + rs.getInt("plan_id") + " | Status: " + rs.getString("subscription_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkSubscriptionStatus(int subId) {
        String sql = "{? = call CheckSubscriptionStatus(?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setInt(2, subId);
            cstmt.execute();
            System.out.println("Subscription ID " + subId + " Live Status (Via Function): " + cstmt.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printPopularPlans() {
        String sql = "SELECT plan_id, COUNT(user_id) AS total_users FROM USER_SUBSCRIPTIONS GROUP BY plan_id HAVING COUNT(user_id) >= 10";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Plan ID: " + rs.getInt("plan_id") + " has " + rs.getInt("total_users") + " active users.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}