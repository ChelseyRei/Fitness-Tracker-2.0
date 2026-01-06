package dao;

import HEAT.model.User;
import HEAT.model.BodyMetric;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    // ==========================================
    // User Profile
    // ==========================================
    public void saveOrUpdateUser(User u) throws SQLException {
        // Check if user exists (ID 1 is default)
        boolean exists = false;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT count(*) FROM user_profile WHERE id = 1")) {
            if (rs.next() && rs.getInt(1) > 0) exists = true;
        }

        String sql;
        if (exists) {
            sql = "UPDATE user_profile SET name=?, age=?, height_cm=?, weight_kg=?, sex=?, BMI=?, BMR=? WHERE id=1";
        } else {
            sql = "INSERT INTO user_profile (name, age, height_cm, weight_kg, sex, BMI, BMR, current_streak, last_workout_date, id) VALUES (?, ?, ?, ?, ?, ?, ?, 0, NULL, 1)";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, u.getName());
            pstmt.setInt(2, u.getAge());
            pstmt.setDouble(3, u.getHeightCm());
            pstmt.setDouble(4, u.getWeightKg());
            pstmt.setString(5, u.getSex());
            pstmt.setDouble(6, u.getBMI());
            pstmt.setDouble(7, u.getBMR());
            pstmt.executeUpdate();
        }
    }

    public User getUser() throws SQLException {
        String sql = "SELECT * FROM user_profile WHERE id = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String dateStr = rs.getString("last_workout_date");
                LocalDate lastDate = (dateStr != null) ? LocalDate.parse(dateStr) : null;
                return new User(
                    rs.getString("name"), rs.getInt("age"), rs.getDouble("height_cm"),
                    rs.getDouble("weight_kg"), rs.getString("sex"), rs.getDouble("BMI"),
                    rs.getDouble("BMR"), rs.getInt("current_streak"), lastDate
                );
            }
        }
        return null;
    }

    // ==========================================
    // Body Metrics
    // ==========================================
    public void insertBodyMetric(BodyMetric bm) throws SQLException {
        String sql = "INSERT INTO body_metrics (age, height_cm, weight_kg, BMI, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bm.getAge());
            pstmt.setDouble(2, bm.getHeightCm());
            pstmt.setDouble(3, bm.getWeightKg());
            pstmt.setDouble(4, bm.getBMI());
            pstmt.setString(5, bm.getDate().toString());
            pstmt.executeUpdate();
        }
    }

    public List<BodyMetric> loadBodyMetrics() throws SQLException {
        List<BodyMetric> metrics = new ArrayList<>();
        String sql = "SELECT * FROM body_metrics ORDER BY date DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                metrics.add(new BodyMetric(
                    rs.getInt("id"), rs.getInt("age"), rs.getDouble("height_cm"),
                    rs.getDouble("weight_kg"), rs.getDouble("BMI"), 
                    LocalDate.parse(rs.getString("date"))
                ));
            }
        }
        return metrics;
    }

    // ==========================================
    // Streak Logic
    // ==========================================
    public void updateUserStreak() throws SQLException {
        String selectSql = "SELECT current_streak, last_workout_date FROM user_profile WHERE id = 1";
        String updateSql = "UPDATE user_profile SET current_streak = ?, last_workout_date = ? WHERE id = 1";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSql)) {
            if (rs.next()) {
                int currentStreak = rs.getInt("current_streak");
                String lastDateStr = rs.getString("last_workout_date");
                LocalDate today = LocalDate.now();
                
                int newStreak = 1; // Default if starting fresh
                
                if (lastDateStr != null) {
                    LocalDate lastDate = LocalDate.parse(lastDateStr);
                    long daysBetween = ChronoUnit.DAYS.between(lastDate, today);

                    if (daysBetween == 0) return; // Already logged today
                    if (daysBetween == 1) newStreak = currentStreak + 1; // Consecutive day
                    // If daysBetween > 1, streak resets to 1 (which is already set)
                }

                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setInt(1, newStreak);
                    pstmt.setString(2, today.toString());
                    pstmt.executeUpdate();
                }
            }
        }
    }
}