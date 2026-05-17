package dao;

import db.DBConnection;
import java.sql.*;

public class WatchHistoryDAO {
    public boolean addWatchHistory(int userId, int contentId, int watchTime) {
        String sql = "{CALL AddWatchHistory(?, ?, ?)}";
        try (Connection conn = DBConnection.getConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.setInt(2, contentId);
            cstmt.setInt(3, watchTime);
            return cstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void printTotalViewsPerContent() {
        String sql = "SELECT content_id, COUNT(*) AS total_views FROM WATCH_HISTORY GROUP BY content_id";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Content ID: " + rs.getInt("content_id") + " | Total Views: " + rs.getInt("total_views"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMostWatchedContent() {
        String sql = "SELECT content_id, COUNT(*) AS total_views FROM WATCH_HISTORY GROUP BY content_id ORDER BY total_views DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Most Watched Content ID: " + rs.getInt("content_id") + " with " + rs.getInt("total_views") + " views.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}