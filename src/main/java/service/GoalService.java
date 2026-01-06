package service;

import dao.GoalDAO;
import model.Goal;
import model.GoalStatus;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GoalService {
    private GoalDAO goalDAO;

    public GoalService() {
        this.goalDAO = new GoalDAO();
    }

    public boolean addGoal(Goal goal) {
        try {
            goalDAO.addGoal(goal);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Goal> getAllGoals() {
        try {
            return goalDAO.loadGoals();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean deleteGoal(int goalId) {
        try {
            goalDAO.deleteGoal(goalId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateProgress(int goalId, double currentValue, boolean isComplete) {
        try {
            GoalStatus status = isComplete ? GoalStatus.COMPLETED : GoalStatus.IN_PROGRESS;
            goalDAO.updateGoalProgress(goalId, currentValue, status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}