package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.UserService;
import model.User;
import java.io.IOException;

public class EditProfileController {

    // --- Inputs (Editable) ---
    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private TextField ageField;
    
    // --- Displays (Read-Only) ---
    @FXML private Label firstName;
    @FXML private Label lastName;
    @FXML private Label bmiValue;
    @FXML private Label bmrValue;
    
    // --- Buttons ---
    @FXML private Button saveButton; 
    @FXML private Button cancelButton;
    @FXML private Button backButton;

    private final UserService userService = new UserService();
    private User currentUser;

    @FXML
    public void initialize() {
        // 1. Load User Data
        currentUser = userService.getUserProfile();
        
        if (currentUser != null) {
            // Fill Inputs
            weightField.setText(String.valueOf(currentUser.getWeightKg()));
            heightField.setText(String.valueOf(currentUser.getHeightCm()));
            ageField.setText(String.valueOf(currentUser.getAge()));
            
            // Fill Read-Only Labels
            bmiValue.setText(String.format("%.1f", currentUser.getBMI()));
            bmrValue.setText(String.format("%.0f", currentUser.getBMR()));
            
            // Split Name for Display
            String fullName = currentUser.getName();
            int spaceIndex = fullName.indexOf(" ");
            if (spaceIndex > 0) {
                firstName.setText(fullName.substring(0, spaceIndex));
                lastName.setText(fullName.substring(spaceIndex + 1));
            } else {
                firstName.setText(fullName);
                lastName.setText("");
            }
        }

        // 2. Setup Button Actions
        saveButton.setOnAction(e -> handleSave());
        
        // Navigation buttons: all go back to Dashboard
        cancelButton.setOnAction(e -> navigate("MainDashboard"));
        backButton.setOnAction(e -> navigate("MainDashboard"));
    }

    private void handleSave() {
        try {
            double newWeight = Double.parseDouble(weightField.getText());
            double newHeight = Double.parseDouble(heightField.getText());
            int newAge = Integer.parseInt(ageField.getText());

            if (currentUser != null) {
                // Update the user in the database
                userService.registerOrUpdateUser(
                    currentUser.getName(), 
                    newAge, 
                    newHeight, 
                    newWeight, 
                    currentUser.getSex(), 
                    currentUser.getActivityLevel() // Keep existing activity level
                );
                
                System.out.println("Profile updated successfully!");
                navigate("MainDashboard");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid numbers entered. Please check fields.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxml);
        }
    }
}