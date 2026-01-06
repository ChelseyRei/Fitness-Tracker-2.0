package dao;

import model.Workout;
import model.StrengthWorkout;
import model.CardioWorkout;
import model.PersonalRecord;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutDAO {
    private Connection conn;

    public WorkoutDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    // ==========================================
    // CRUD Operations
    // ==========================================
    public void saveWorkout(Workout w) throws SQLException {
        String sql;
        if (w instanceof StrengthWorkout) {
            sql = "INSERT INTO workouts (exercise_name, type, date, duration_minutes, calories_burned, sets, reps, weight_kg, volume_kg, bodyweight_factor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO workouts (exercise_name, type, date, duration_minutes, calories_burned, distance_km) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, w.getName());
            pstmt.setString(2, w.getType());
            pstmt.setString(3, w.getDate().toString());
            pstmt.setInt(4, w.getDurationMinutes());
            pstmt.setDouble(5, w.getCaloriesBurned());

            if (w instanceof StrengthWorkout sw) {
                pstmt.setInt(6, sw.getSetCount());
                pstmt.setInt(7, sw.getRepCount());
                pstmt.setDouble(8, sw.getExternalWeightKg());
                pstmt.setDouble(9, sw.getTrainingVolumeKg());
                pstmt.setDouble(10, sw.getBodyWeightFactor());
            } else if (w instanceof CardioWorkout cw) {
                pstmt.setDouble(6, cw.getDistanceKm());
            }
            pstmt.executeUpdate();
        }
    }

    public List<Workout> loadWorkouts() throws SQLException {
        List<Workout> list = new ArrayList<>();
        String sql = "SELECT * FROM workouts ORDER BY date DESC, id DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String type = rs.getString("type");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                
                if ("Strength".equalsIgnoreCase(type)) {
                    list.add(new StrengthWorkout(
                        rs.getInt("id"), rs.getString("exercise_name"), type, date,
                        rs.getDouble("calories_burned"), rs.getInt("duration_minutes"),
                        rs.getInt("sets"), rs.getInt("reps"), rs.getDouble("weight_kg"),
                        rs.getDouble("volume_kg"), rs.getDouble("bodyweight_factor")
                    ));
                } else {
                    list.add(new CardioWorkout(
                        rs.getInt("id"), rs.getString("exercise_name"), type, date,
                        rs.getDouble("calories_burned"), rs.getInt("duration_minutes"),
                        rs.getDouble("distance_km")
                    ));
                }
            }
        }
        return list;
    }
    
    public void deleteWorkout(int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM workouts WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // ==========================================
    // Personal Records (PRs)
    // ==========================================
    public Map<String, PersonalRecord> loadPersonalRecords() throws SQLException {
        Map<String, PersonalRecord> map = new HashMap<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM personal_records")) {
            while (rs.next()) {
                String name = rs.getString("exercise_name");
                map.put(name, new PersonalRecord(
                    name, rs.getInt("duration_minutes"), rs.getInt("reps"),
                    rs.getDouble("weight_kg"), LocalDate.parse(rs.getString("date"))
                ));
            }
        }
        return map;
    }

    public void updatePersonalRecord(String exerciseName, double weight, int reps, int duration, LocalDate date) throws SQLException {
        // Try update first
        String update = "UPDATE personal_records SET weight_kg=?, reps=?, duration_minutes=?, date=? WHERE exercise_name=?";
        try (PreparedStatement pstmt = conn.prepareStatement(update)) {
            pstmt.setDouble(1, weight);
            pstmt.setInt(2, reps);
            pstmt.setInt(3, duration);
            pstmt.setString(4, date.toString());
            pstmt.setString(5, exerciseName);
            if (pstmt.executeUpdate() == 0) {
                // If update failed, insert new
                String insert = "INSERT INTO personal_records (exercise_name, weight_kg, reps, duration_minutes, date) VALUES (?,?,?,?,?)";
                try (PreparedStatement insertStmt = conn.prepareStatement(insert)) {
                    insertStmt.setString(1, exerciseName);
                    insertStmt.setDouble(2, weight);
                    insertStmt.setInt(3, reps);
                    insertStmt.setInt(4, duration);
                    insertStmt.setString(5, date.toString());
                    insertStmt.executeUpdate();
                }
            }
        }
    }
}