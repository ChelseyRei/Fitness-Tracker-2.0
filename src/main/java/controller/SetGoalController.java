package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.GoalService;
import model.Goal;
import model.GoalStatus;

import java.io.IOException;
import java.time.LocalDate;

public class SetGoalController {

    // --- FXML Fields (Matched exactly to SetGoal.fxml) ---
    @FXML private ComboBox<String> goalTypeComboBox;
    @FXML private TextArea goalDescriptionTextArea;
    @FXML private TextField targetTextField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private Button saveGoalButton;

    // --- Navigation Buttons ---
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final GoalService goalService = new GoalService();

    @FXML
    public void initialize() {
        // 1. Populate Dropdown
        goalTypeComboBox.getItems().addAll("Weight Loss", "Muscle Gain", "Consistency", "Endurance");
        
        // 2. Set Default Date
        startDatePicker.setValue(LocalDate.now());

        // 3. Setup Button Actions
        saveGoalButton.setOnAction(e -> handleSave());

        // 4. Setup Navigation Bar
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        // We are already on "SetGoal", so the button can reload or do nothing
        goalsNavButton.setOnAction(e -> navigate("SetGoal")); 
    }

    private void handleSave() {
        try {
            String type = goalTypeComboBox.getValue();
            String description = goalDescriptionTextArea.getText();
            String targetStr = targetTextField.getText();
            LocalDate start = startDatePicker.getValue();
            LocalDate end = endDatePicker.getValue();

            // Basic Validation
            if (type == null || description.isEmpty() || targetStr.isEmpty() || end == null) {
                System.out.println("Please fill in all fields.");
                return;
            }

            double target = Double.parseDouble(targetStr);

            Goal goal = new Goal(
                0, 
                description, 
                "General", 
                start, 
                end, 
                type, 
                0.0, 
                target, 
                GoalStatus.IN_PROGRESS
            );

            // Save to Database
            if (goalService.addGoal(goal)) {
                System.out.println("Goal saved successfully!");
                navigate("MainDashboard");
            } else {
                System.err.println("Failed to save goal.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number for target value.");
        }
    }

    // Helper method for navigation
    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error navigating to " + fxml);
        }
    }
}