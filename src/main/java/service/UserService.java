package service;

import dao.UserDAO;
import model.User;
import model.BodyMetric;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerOrUpdateUser(String name, int age, double height, double weight, String sex, String activityLevel) {
        // Calculate BMR (Mifflin-St Jeor Equation)
        double bmr;
        if (sex.equalsIgnoreCase("Male")) {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;
        } else {
            bmr = (10 * weight) + (6.25 * height) - (5 * age) - 161;
        }

        // Calculate BMI
        double heightM = height / 100.0;
        double bmi = weight / (heightM * heightM);

        User user = new User(name, age, height, weight, sex, bmi, bmr);
        
        try {
            userDAO.saveOrUpdateUser(user);
            // Also log this as the first body metric
            BodyMetric initialMetric = new BodyMetric(0, age, height, weight, bmi, LocalDate.now());
            userDAO.insertBodyMetric(initialMetric);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserProfile() {
        try {
            return userDAO.getUser();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void refreshStreak() {
        try {
            userDAO.updateUserStreak();
        } catch (SQLException e) {
            System.err.println("Failed to update streak: " + e.getMessage());
        }
    }
}
