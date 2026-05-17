package dao;

import db.DBConnection;
import model.Content;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContentDAO {
    public List<Content> getContentSortedByYear() {
        List<Content> contents = new ArrayList<>();
        String sql = "SELECT * FROM CONTENT ORDER BY release_year DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contents.add(new Content(
                    rs.getInt("content_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getInt("release_year"),
                    rs.getString("content_type"),
                    rs.getString("age_rating"),
                    rs.getString("language"),
                    rs.getInt("duration_minutes")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contents;
    }
}