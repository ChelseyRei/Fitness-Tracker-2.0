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
    @FXML private Button saveWorkoutButton; // Matches fx:id in FXML
    
    // Bottom Navigation
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        // 1. Populate Dropdown
        activityComboBox.getItems().addAll("Running", "Cycling", "Swimming", "Walking", "Jumping Jacks");

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
            String activity = activityComboBox.getValue();
            String durationText = durationField.getText();

            if (activity == null || durationText.isEmpty()) {
                System.out.println("Please select an activity and enter duration.");
                return;
            }

            int duration = Integer.parseInt(durationText);
            // Estimate calories (approx 8 per minute)
            double calories = duration * 8.0; 

            CardioWorkout workout = new CardioWorkout(0, activity, "Cardio", LocalDate.now(), calories, duration, 0.0);

            if (workoutService.logWorkout(workout)) {
                System.out.println("Saved!");
                navigate("MainDashboard");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
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