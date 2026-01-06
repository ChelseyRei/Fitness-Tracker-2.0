package dao;

import HEAT.model.Goal;
import HEAT.model.GoalStatus;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {
    private Connection conn;

    public GoalDAO() {
        this.conn = DatabaseConnection.getInstance().getConnection();
    }

    public void addGoal(Goal g) throws SQLException {
        String sql = "INSERT INTO goals (goal_title, exercise_name, start_date, end_date, goal_type, current_value, target_value, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, g.getGoalTitle());
            pstmt.setString(2, g.getExerciseName());
            pstmt.setString(3, g.getStartDate().toString());
            pstmt.setString(4, g.getEndDate() != null ? g.getEndDate().toString() : null);
            pstmt.setString(5, g.getGoalType());
            pstmt.setDouble(6, g.getCurrentValue());
            pstmt.setDouble(7, g.getTargetValue());
            pstmt.setString(8, g.getStatus().name());
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) g.setId(rs.getInt(1));
            }
        }
    }

    public List<Goal> loadGoals() throws SQLException {
        List<Goal> list = new ArrayList<>();
        String sql = "SELECT * FROM goals ORDER BY id DESC";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                LocalDate end = rs.getString("end_date") != null ? LocalDate.parse(rs.getString("end_date")) : null;
                list.add(new Goal(
                    rs.getInt("id"), rs.getString("goal_title"), rs.getString("exercise_name"),
                    LocalDate.parse(rs.getString("start_date")), end,
                    rs.getString("goal_type"), rs.getDouble("current_value"),
                    rs.getDouble("target_value"), GoalStatus.valueOf(rs.getString("status"))
                ));
            }
        }
        return list;
    }

    public void updateGoalProgress(int id, double currentVal, GoalStatus status) throws SQLException {
        String sql = "UPDATE goals SET current_value = ?, status = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, currentVal);
            pstmt.setString(2, status.name());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteGoal(int id) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM goals WHERE id = ?")) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}