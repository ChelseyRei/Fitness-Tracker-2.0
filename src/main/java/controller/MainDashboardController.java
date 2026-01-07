package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import service.UserService;
import service.WorkoutService;
import model.User;
import model.Workout;

import java.io.IOException;
import java.util.List;

public class MainDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Label streakLabel;
    @FXML private Label bmiLabel;
    @FXML private Label weightLabel;
    @FXML private ProgressBar goalProgressBar; // Example UI element

    private final UserService userService = new UserService();
    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        loadUserData();
    }

    private void loadUserData() {
        User user = userService.getUserProfile();
        if (user != null) {
            welcomeLabel.setText("Welcome back, " + user.getName() + "!");
            streakLabel.setText(String.valueOf(user.getCurrentStreak()));
            bmiLabel.setText(String.format("%.1f", user.getBMI()));
            weightLabel.setText(String.format("%.1f kg", user.getWeightKg()));
            
            // Check streak on login
            userService.refreshStreak();
        }
    }

    // Navigation Events
    @FXML
    private void handleLogWorkout() throws IOException {
        Main.setRoot("LogSelection");
    }

    @FXML
    private void handleViewHistory() throws IOException {
        Main.setRoot("ProgressReport"); // Assuming you have this view
    }

    @FXML
    private void handleEditProfile() throws IOException {
        Main.setRoot("EditProfile");
    }
    
    @FXML
    private void handleGoals() throws IOException {
        Main.setRoot("SetGoal");
    }
}