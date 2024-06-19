/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.RecentActivity;
import util.DBUtil;

/**
 *
 * @author User
 */
public class RecentActivityDAO {

    private final String NEW_ACTIVITY = "INSERT INTO recentActivities (userId,action,timestamp) VALUES (?, ?, ?)";
    private final String GET_RECENT_ACTIVITIES = "SELECT action, timestamp FROM recentActivities WHERE userId = ? ORDER BY timestamp DESC LIMIT 5";

    public boolean newActivity(RecentActivity recentActivity) {
        try (
                Connection connection = DBUtil.getConnection(); PreparedStatement stmt = connection.prepareStatement(NEW_ACTIVITY);) {
            stmt.setString(1, recentActivity.getUserId());
            stmt.setString(2, recentActivity.getAction());
            stmt.setTimestamp(3, recentActivity.getTimestamp());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RecentActivity> getRecentActivitiesByUser(String userId) throws SQLException {
        List<RecentActivity> recentActivities = new ArrayList<>();
        Connection connection = DBUtil.getConnection();
        try {
            // Create SQL query

            // Create prepared statement
            PreparedStatement statement = connection.prepareStatement(GET_RECENT_ACTIVITIES);
            statement.setString(1, userId);

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process result set
            while (resultSet.next()) {
                // Retrieve data from result set and create RecentActivity object
                String action = resultSet.getString("action");
                String timestamp = resultSet.getString("timestamp");

                // Create RecentActivity object and add it to the list
                RecentActivity activity = new RecentActivity();
                activity.setUserId(userId);
                activity.setAction(action);
                activity.setTimestamp(Timestamp.valueOf(timestamp));
                recentActivities.add(activity);
            }

            // Close resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentActivities;
    }
}
