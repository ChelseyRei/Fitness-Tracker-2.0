package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.WorkoutService;
import model.StrengthWorkout;
import java.io.IOException;
import java.time.LocalDate;

public class LogStrengthController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private ComboBox<String> exerciseComboBox;
    @FXML private TextField setsField;
    @FXML private TextField repsField;
    @FXML private TextField weightField;
    @FXML private Button saveWorkoutButton;
    
    // Bottom Navigation
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        // 1. Populate Dropdown
        exerciseComboBox.getItems().addAll("Bench Press", "Squats", "Deadlift", "Bicep Curls", "Push-ups");

        // 2. Setup Action Buttons
        saveWorkoutButton.setOnAction(e -> handleSave());
        profileButton.setOnAction(e -> navigate("EditProfile"));
        backButton.setOnAction(e -> navigate("LogSelection"));

        // 3. Setup Navigation Bar
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));
    }

    private void handleSave() {
        try {
            String name = exerciseComboBox.getValue();
            int sets = Integer.parseInt(setsField.getText());
            int reps = Integer.parseInt(repsField.getText());
            double weight = Double.parseDouble(weightField.getText());

            // Basic Calculations
            double volume = sets * reps * weight;
            int estimatedDuration = sets * 3; // Approx 3 mins per set
            double calories = estimatedDuration * 3.5;

            StrengthWorkout workout = new StrengthWorkout(
                0, name, "Strength", LocalDate.now(), calories, estimatedDuration,
                sets, reps, weight, volume, 0.0
            );

            if (workoutService.logWorkout(workout)) {
                System.out.println("Saved!");
                navigate("MainDashboard");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please check numbers.");
        }
    }

    private void navigate(String fxml) {
        try {
            Main.setRoot(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}