package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import service.UserService;
import model.User;

import java.io.IOException;

public class RegistrationSummaryController {

    @FXML private Button backButton;
    @FXML private Button confirmButton;
    @FXML private Label birthdayValue;
    @FXML private Label ageValue;
    @FXML private Label lastName;
    @FXML private Label firstName;
    @FXML private Label weightValue;
    @FXML private Label heightValue;
    @FXML private Label bmiValue;
    @FXML private Label bmrValue;

    // Connect to Backend Service
    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        loadUserData();
        setupEventHandlers();
    }

    private void loadUserData() {
        User user = userService.getUserProfile();
        
        if (user != null) {
            parseAndSetName(user.getName());
            
            // Format metrics with units
            weightValue.setText(String.format("%.1f kg", user.getWeightKg()));
            heightValue.setText(String.format("%.1f cm", user.getHeightCm()));
            
            // BMI & BMR are already calculated by the service/database
            bmiValue.setText(String.format("%.1f", user.getBMI()));
            bmrValue.setText(String.format("%.0f", user.getBMR()));

            // Handle Age
            // Note: The database stores Age, not the exact Birthday date.
            ageValue.setText("Age: " + user.getAge());
            birthdayValue.setText(""); // Clear this or set to "N/A" since we don't store the date
        }
    }

    private void parseAndSetName(String fullName) {
        if (fullName == null) return;
        
        // Simple logic to split First/Last name for the summary display
        // Assuming user entered "First Last" or "First Middle Last"
        String[] parts = fullName.trim().split("\\s+");
        
        if (parts.length > 0) {
            if (parts.length == 1) {
                firstName.setText(parts[0]);
                lastName.setText("");
            } else {
                firstName.setText(parts[0]);
                // Join the rest as the last name
                StringBuilder last = new StringBuilder();
                for (int i = 1; i < parts.length; i++) {
                    last.append(parts[i]).append(" ");
                }
                lastName.setText(last.toString().trim());
            }
        }
    }

    private void setupEventHandlers() {
        // Use lambdas for safe navigation
        backButton.setOnAction(e -> navigate("RegistrationForm"));
        confirmButton.setOnAction(e -> navigate("MainDashboard"));
    }

    // Helper method to use your Main.setRoot navigation
    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxml);
        }
    }
}