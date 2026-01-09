package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import service.WorkoutService;
import model.Workout;
import model.StrengthWorkout;
import model.CardioWorkout;
import java.io.IOException;
import java.util.List;

public class ProgressReportController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private ListView<String> workoutListView; // Assumes you have a ListView in your FXML
    @FXML private Label totalWorkoutsLabel;
    
    // Navigation Buttons
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        loadProgressData();
        setupEventHandlers();
    }

    private void loadProgressData() {
        List<Workout> workouts = workoutService.getWorkoutHistory();
        
        // Example: Update a label with total count
        if (totalWorkoutsLabel != null) {
            totalWorkoutsLabel.setText(String.valueOf(workouts.size()));
        }

        // Populate list if you have one in your FXML
        if (workoutListView != null) {
            workoutListView.getItems().clear();
            for (Workout w : workouts) {
                String desc = w.getName() + " (" + w.getDate() + ")";
                workoutListView.getItems().add(desc);
            }
        }
    }

    private void setupEventHandlers() {
        // Top Navigation
        backButton.setOnAction(e -> navigate("MainDashboard"));
        profileButton.setOnAction(e -> navigate("EditProfile"));

        // Bottom Navigation
        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport")); // Already here
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));
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