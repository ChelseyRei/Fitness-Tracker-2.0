package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.WorkoutService;
import model.CardioWorkout;

import java.io.IOException;
import java.time.LocalDate;

public class LogCardioController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private ComboBox<String> activityComboBox;
    @FXML private TextField durationField;
    @FXML private Button saveWorkoutButton;
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    // Connect to the backend service
    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        loadActivities();
        setupEventHandlers();
    }

    private void loadActivities() {
        activityComboBox.getItems().addAll(
            "Running",
            "Cycling",
            "Walking",
            "Swimming",
            "Jumping Jacks",
            "Mountain Climber",
            "Dancing",
            "Boxing",
            "Basketball",
            "Tennis"
        );
    }

    private void setupEventHandlers() {
        // Use lambdas to handle navigation
        backButton.setOnAction(e -> navigate("LogSelection"));
        profileButton.setOnAction(e -> navigate("EditProfile"));
        saveWorkoutButton.setOnAction(e -> saveWorkout());
        
        // Navigation Bar
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));
    }

    private void saveWorkout() {
        String activity = activityComboBox.getValue();
        String durationText = durationField.getText();

        if (activity == null || durationText.isEmpty()) {
            System.out.println("Please select an activity and enter duration.");
            return;
        }

        try {
            int durationMinutes = Integer.parseInt(durationText);
            
            // Simple Calorie Burn Estimate (approx 8 calories per minute for moderate cardio)
            double caloriesBurned = durationMinutes * 8.0; 
            
            // Create the workout object
            // Note: Distance is set to 0.0 since this specific screen doesn't have a distance input
            CardioWorkout workout = new CardioWorkout(
                0,                  // ID (auto-generated)
                activity,           // Name
                "Cardio",           // Type
                LocalDate.now(),    // Date (Today)
                caloriesBurned,     // Calories
                durationMinutes,    // Duration
                0.0                 // Distance (default)
            );

            // Save to Database
            boolean success = workoutService.logWorkout(workout);

            if (success) {
                // Check for Personal Record (Longest duration)
                workoutService.checkPersonalRecord(activity, 0, 0, durationMinutes);
                
                System.out.println("Workout saved successfully!");
                clearFields();
                navigate("MainDashboard");
            } else {
                System.err.println("Failed to save workout.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid duration input. Please enter a number.");
        }
    }

    private void clearFields() {
        activityComboBox.setValue(null);
        durationField.clear();
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