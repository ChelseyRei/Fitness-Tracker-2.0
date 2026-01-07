package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import service.WorkoutService;
import model.Workout;
import java.io.IOException;
import java.util.List;

public class LogSelectionController {

    @FXML private Button backButton;
    @FXML private Button profileButton;
    @FXML private Button strengthButton;
    @FXML private Button cardioButton;
    @FXML private VBox historyList;
    
    // Bottom Navigation
    @FXML private Button summaryNavButton;
    @FXML private Button homeNavButton;
    @FXML private Button goalsNavButton;

    private final WorkoutService workoutService = new WorkoutService();

    @FXML
    public void initialize() {
        // 1. Navigation
        strengthButton.setOnAction(e -> navigate("LogStrength"));
        cardioButton.setOnAction(e -> navigate("LogCardio"));
        
        backButton.setOnAction(e -> navigate("MainDashboard"));
        profileButton.setOnAction(e -> navigate("EditProfile"));

        homeNavButton.setOnAction(e -> navigate("MainDashboard"));
        summaryNavButton.setOnAction(e -> navigate("ProgressReport"));
        goalsNavButton.setOnAction(e -> navigate("SetGoal"));

        // 2. Load History
        loadHistory();
    }

    private void loadHistory() {
        historyList.getChildren().clear();
        List<Workout> workouts = workoutService.getWorkoutHistory();
        if (workouts.isEmpty()) {
            historyList.getChildren().add(new Label("No recent workouts."));
        } else {
            for (Workout w : workouts) {
                // Simple label for now
                Label label = new Label(w.getName() + " - " + w.getDate());
                label.getStyleClass().add("history-workout-name"); 
                historyList.getChildren().add(label);
            }
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