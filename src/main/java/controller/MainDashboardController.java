package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.UserService;
import model.User;
import java.io.IOException;
import java.time.LocalDate;

public class MainDashboardController {

    // --- MATCHING FXML IDs ---
    @FXML private Label streakText;  // Renamed from 'streakLabel' to match FXML
    @FXML private Label dateLabel;   // Added because it exists in your FXML

    // --- MISSING IN FXML (Commented out to stop the crash) ---
    // To use these, you must add Labels with these fx:id's to your MainDashboard.fxml file first.
    // @FXML private Label welcomeLabel; 
    // @FXML private Label bmiLabel;
    // @FXML private Label weightLabel;
    // @FXML private ProgressBar goalProgressBar;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        // 1. Set the date
        if (dateLabel != null) {
            dateLabel.setText(LocalDate.now().toString());
        }

        // 2. Load User Data
        loadUserData();
    }

    private void loadUserData() {
        User user = userService.getUserProfile();
        if (user != null) {
            // This label is missing in FXML, so we skip it for now
            // welcomeLabel.setText("Welcome back, " + user.getName() + "!");
            
            // This ID matches now, so it will work
            if (streakText != null) {
                streakText.setText(String.valueOf(user.getCurrentStreak()) + " Days");
            }

            // These are missing in FXML, so we skip them
            // bmiLabel.setText(String.format("%.1f", user.getBMI()));
            // weightLabel.setText(String.format("%.1f kg", user.getWeightKg()));
            
            // Check streak logic
            userService.refreshStreak();
        }
    }

    // --- NAVIGATION ---
    @FXML
    private void handleLogWorkout() throws IOException {
        Main.setRoot("LogSelection");
    }

    @FXML
    private void handleViewHistory() throws IOException {
        Main.setRoot("ProgressReport");
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